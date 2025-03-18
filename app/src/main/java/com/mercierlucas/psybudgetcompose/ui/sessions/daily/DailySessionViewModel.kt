package com.mercierlucas.psybudgetcompose.ui.sessions.daily

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.SessionByDayLite
import com.mercierlucas.psybudgetcompose.data.custom.SessionsByDayStats
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DailySessionViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel()  {


    private val _isProgressBarDisplayedStateFlow = MutableStateFlow(false)
    val isProgressBarDisplayedStateFlow = _isProgressBarDisplayedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _sessionsByDayStateFlow = MutableStateFlow<List<SessionByDayLite>>(emptyList())
    val sessionsByDayStateFlow = _sessionsByDayStateFlow.asStateFlow()

    private val _sessionsByDayStatsStateFlow = MutableStateFlow(
        SessionsByDayStats(0,0,0)
    )
    val sessionsByDayStatsStateFlow = _sessionsByDayStatsStateFlow.asStateFlow()


    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }

    fun getSessionsByDay(strDateFormatted : String){
        viewModelScope.launch {
            try {
                val responseGetSessionsByDay = withContext(Dispatchers.IO){
                    apiService.getSessionsByDay(myPrefs.token, strDateFormatted)
                }
                val body = responseGetSessionsByDay?.body()
                when{
                    responseGetSessionsByDay == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetSessionsByDay.isSuccessful && body !=null ->{
                        _sessionsByDayStateFlow.value = body.sessions.map {
                            sessionDto ->
                            SessionByDayLite(
                                sessionId = sessionDto.id,
                                sessionDate = sessionDto.sessionDate,
                                patientId = sessionDto.patient.id,
                                patientFirstName = sessionDto.patient.firstName,
                                patientLastNAme = sessionDto.patient.lastName
                            )
                        }
                        _sessionsByDayStatsStateFlow.value = SessionsByDayStats(
                            numberOfCompletedSessions = body.numberOfCompletedSessions ?: 0,
                            totalAmountPaid = body.totalAmountPaid ?: 0,
                            totalAmountToBeValidated = body.totalAmountToBeValidated ?: 0
                        )
                    }
                    else -> {
                        when(responseGetSessionsByDay.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.user_not_found)
                            else -> responseGetSessionsByDay.errorBody()?.let {
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

    fun goToDetailedSession(sessionId: Long) {

    }


}