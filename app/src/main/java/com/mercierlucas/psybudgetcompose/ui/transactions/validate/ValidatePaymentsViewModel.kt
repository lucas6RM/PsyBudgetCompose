package com.mercierlucas.psybudgetcompose.ui.transactions.validate

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.feedarticlesjetpack.utils.convertMillisToDate
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDueThisYear
import com.mercierlucas.psybudgetcompose.data.network.dtos.UpdateTransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    init {
        val currentDateFormatted = convertMillisToDate(Calendar.getInstance().timeInMillis)
        getAllSessionsDueThisYear(currentDateFormatted)
    }



    fun getAllSessionsDueThisYear(strDateFormatted : String){
        viewModelScope.launch {
            _isProgressBarDisplayedStateFlow.value = true

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
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
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
            _isProgressBarDisplayedStateFlow.value = false
        }
    }

    fun updatePaymentValidated(sessionDueThisYear: SessionDueThisYear) {

        val transactionId = sessionDueThisYear.transaction.id
        val updateTransactionDto = UpdateTransactionDto(
            amountDue = sessionDueThisYear.transaction.amountDue,
            patientId = sessionDueThisYear.patient.id,
            paymentMethod = sessionDueThisYear.transaction.paymentMethod,
            isPaymentValidated = true
        )

        viewModelScope.launch {
            _isProgressBarDisplayedStateFlow.value = true

            try {
                val responseUpdateTransaction = withContext(Dispatchers.IO){
                    apiService.updateTransactionToBePaid(myPrefs.token,transactionId,updateTransactionDto)
                }
                val body = responseUpdateTransaction?.body()
                when{
                    responseUpdateTransaction == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseUpdateTransaction.isSuccessful && body !=null ->{
                        println(body.updatedAt)
                        println(body.id)
                        displayToast(R.string.payment_validated)
                        getAllSessionsDueThisYear(sessionDueThisYear.sessionDate)
                    }
                    else -> {
                        when(responseUpdateTransaction.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.transaction_not_found)
                            else -> responseUpdateTransaction.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
            _isProgressBarDisplayedStateFlow.value = false
        }

    }


}