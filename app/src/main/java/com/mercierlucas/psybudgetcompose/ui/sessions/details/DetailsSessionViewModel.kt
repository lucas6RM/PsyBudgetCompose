package com.mercierlucas.psybudgetcompose.ui.sessions.details

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.SessionByDayLite
import com.mercierlucas.psybudgetcompose.data.custom.SessionsByDayStats
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDto
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
class DetailsSessionViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel(){


    private val _isProgressBarDisplayedStateFlow = MutableStateFlow(false)
    val isProgressBarDisplayedStateFlow = _isProgressBarDisplayedStateFlow.asStateFlow()

    private val _sessionIdentifiedStateFlow = MutableStateFlow<SessionDto?>(null)
    val sessionIdentifiedStateFlow = _sessionIdentifiedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _goToDailySessionsSharedFlow = MutableSharedFlow<Boolean>()
    val goToDailySessionsSharedFlow = _goToDailySessionsSharedFlow.asSharedFlow()

    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }

    private fun goToDailySessions(){
        viewModelScope.launch{
            _goToDailySessionsSharedFlow.emit(true)
        }
    }



    fun getSessionById(idSessionSelected: Long) {
        viewModelScope.launch {
            try {
                val responseGetSessionById = withContext(Dispatchers.IO){
                    apiService.getSessionById(myPrefs.token,idSessionSelected)
                }
                val body = responseGetSessionById?.body()
                when{
                    responseGetSessionById == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetSessionById.isSuccessful && body !=null ->{
                        _sessionIdentifiedStateFlow.value = body
                    }
                    else -> {
                        when(responseGetSessionById.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.session_not_found)
                            else -> responseGetSessionById.errorBody()?.let {
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

    fun deleteSession(idSessionToDelete: Long) {
        viewModelScope.launch {
            try {
                val responseDeleteSession = withContext(Dispatchers.IO){
                    apiService.deleteSessionById(myPrefs.token,idSessionToDelete)
                }
                val body = responseDeleteSession?.body()
                when{
                    responseDeleteSession == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseDeleteSession.isSuccessful && body !=null ->{
                        displayToast(R.string.session_deleted)
                        goToDailySessions()
                    }
                    else -> {
                        when(responseDeleteSession.code()){
                            400 -> displayToast(R.string.bad_request)
                            404 -> displayToast(R.string.session_not_found)
                            else -> responseDeleteSession.errorBody()?.let {
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