package com.mercierlucas.psybudgetcompose.ui.transactions.validate

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.SessionByDayLite
import com.mercierlucas.psybudgetcompose.data.custom.SessionsByDayStats
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDueThisYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ValidatePaymentsViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {


    private val _isProgressBarDisplayedStateFlow = MutableStateFlow(false)
    val isProgressBarDisplayedStateFlow = _isProgressBarDisplayedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _sessionsDueThisYearStateFlow = MutableStateFlow<List<SessionDueThisYear>>(emptyList())
    val sessionsDueThisYearStateFlow = _sessionsDueThisYearStateFlow.asStateFlow()


    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }



    fun getAllSessionsDueThisYear(strDateFormatted : String){
        viewModelScope.launch {
            try {
                val responseGetDueSessions = withContext(Dispatchers.IO){
                    apiService.getSessionsDueThisYear(myPrefs.token,strDateFormatted)
                }
                val body = responseGetDueSessions?.body()
                when{
                    responseGetDueSessions == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetDueSessions.isSuccessful && body !=null ->{
                        _sessionsDueThisYearStateFlow.value = body.sessionsDueThisYear
                    }
                    else -> {
                        when(responseGetDueSessions.code()){
                            404 -> displayToast(R.string.user_not_found)
                            else -> responseGetDueSessions.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
        }
    }



}