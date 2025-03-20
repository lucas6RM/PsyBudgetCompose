package com.mercierlucas.psybudgetcompose.ui.transactions.incomes

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.feedarticlesjetpack.utils.convertMillisToDate
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.PaymentStats
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDueThisYear
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionsThisMonth
import com.mercierlucas.psybudgetcompose.utils.PaymentMethod
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
class IncomesByMonthViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _paymentStatsStateFlow = MutableStateFlow<PaymentStats?>(null)
    val paymentStatsStateFlow = _paymentStatsStateFlow.asStateFlow()

    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }

    init {
        val currentDateFormatted = convertMillisToDate(Calendar.getInstance().timeInMillis)
        getAllSessionsThisMonth(currentDateFormatted)
    }



    fun getAllSessionsThisMonth(strDateFormatted : String){
        viewModelScope.launch {
            try {
                val responseGetSessions = withContext(Dispatchers.IO){
                    apiService.getSessionsThisMonth(myPrefs.token,strDateFormatted)
                }
                val body = responseGetSessions?.body()
                when{
                    responseGetSessions == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetSessions.isSuccessful && body !=null ->{
                        mapSessionsAndStatsCalculationsToDisplay(body.sessionsThisMonth)
                    }
                    else -> {
                        when(responseGetSessions.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.user_not_found)
                            else -> responseGetSessions.errorBody()?.let {
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

    private fun mapSessionsAndStatsCalculationsToDisplay(sessionsThisMonth: List<SessionsThisMonth>) {

        val numberAgreementUsed = sessionsThisMonth.count{ it.isStateAgreementUsed }

        val creditCardSumPaid = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.CB.str
                    && it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val creditCardSumDue = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.CB.str
                    && !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val bankCheckSumPaid = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.CHEQUE.str
                    && it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val bankCheckSumDue = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.CHEQUE.str
                    && !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val bankTransferSumPaid = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.VIREMENT.str
                    && it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val bankTransferSumDue = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.VIREMENT.str
                    && !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val cashSumPaid = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.ESPECES.str
                    && it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val cashSumDue = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.ESPECES.str
                    && !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val thirdPartyPayerSumPaid = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.TIERS_PAYANT.str
                    && it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val thirdPartyPayerSumDue = sessionsThisMonth
            .filter { it.transaction.paymentMethod == PaymentMethod.TIERS_PAYANT.str
                    && !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val totalPaid = sessionsThisMonth
            .filter { it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        val totalDue = sessionsThisMonth
            .filter { !it.transaction.isPaymentValidated }
            .sumOf { it.transaction.amountDue }

        _paymentStatsStateFlow.value = PaymentStats(
            numberAgreementUsed,
            creditCardSumPaid,
            creditCardSumDue,
            bankCheckSumPaid,
            bankCheckSumDue,
            bankTransferSumPaid,
            bankTransferSumDue,
            cashSumPaid,
            cashSumDue,
            thirdPartyPayerSumPaid,
            thirdPartyPayerSumDue,
            totalPaid,
            totalDue
        )

    }



}