package com.mercierlucas.psybudgetcompose.ui.sessions.create

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreateSessionDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.DropDownEnumString
import com.mercierlucas.psybudgetcompose.ui.custom.DropDownPatientsInTherapyList
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.custom.datepickers.DatePickerDocked
import com.mercierlucas.psybudgetcompose.utils.PaymentMethod
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun CreateSessionScreen(
    navController: NavHostController,
    createSessionViewModel: CreateSessionViewModel
) {

    val isProgressBarActive by createSessionViewModel.isProgressBarDisplayedStateFlow
        .collectAsState()
    val activePatientList by createSessionViewModel.activePatientLiteListStateFlow.collectAsState()
    val remainingAgreement by createSessionViewModel.remainingAgreementsStateFlow.collectAsState()
    val patientSelected by createSessionViewModel.patientSelectedStateFlow.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(true) {
        createSessionViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }

    if (activePatientList.isNotEmpty())
        CreateSessionView(
            isProgressBarActive,
            activePatientList,
            onClickUseAgreement = {
                createSessionViewModel.getRemainingAgreementsForThisYear()
            },
            onClickPatientSelected = {patient ->
                createSessionViewModel.setCurrentPatientSelected(patient)
            },
            remainingAgreement = remainingAgreement,
            patientSelected?.id ?: 0L,
            onClickSaveSession = {createSessionDto ->
                createSessionViewModel.validateInputsBeforeSaving(createSessionDto)
            }
        )

    LaunchedEffect(true) {
        delay(5.seconds)
        if (activePatientList.isEmpty())
            context.showToast("Patient list empty ! Go back", Toast.LENGTH_LONG)
    }
}

@Composable
fun CreateSessionView(
    isProgressBarActive: Boolean,
    activePatientList: List<PatientLite>,
    onClickUseAgreement: () -> Unit,
    onClickPatientSelected: (PatientLite) -> Unit,
    remainingAgreement: Int,
    patientSelectedId: Long,
    onClickSaveSession : (CreateSessionDto) -> Unit
) {

    var amount                      by remember { mutableIntStateOf(50) }
    var isUsingAgreement            by remember { mutableStateOf(false) }
    var isSessionCancelled          by remember { mutableStateOf(false) }
    var isPaymentValidated          by remember { mutableStateOf(false) }
    var dateSelected                by remember { mutableStateOf("") }
    var paymentMethodSelected       by remember { mutableStateOf("") }



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ){

        HeaderCustom(stringResource(id = R.string.save_a_new_session))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            DatePickerDocked(
                modifier = Modifier.padding(horizontal = 20.dp),
                textLabel = stringResource(id = R.string.session_date),
                onDateSelected = { strFormattedDate ->
                    dateSelected = strFormattedDate ?: "" }
            )

            DropDownPatientsInTherapyList(
                stringResource(
                    id = R.string.list_of_patient_in_therapy),
                    patientsInTherapy = activePatientList,
                    onClickPatientSelected = onClickPatientSelected,
                    onPatientChange = {
                    isUsingAgreement = false
                }
            )

            DropDownEnumString(
                titleList = "Payment Method :",
                PaymentMethod.entries,
                onPaymentMethodSelected = { paymentMethod ->
                    paymentMethodSelected = paymentMethod
                }
            )

            OutlinedTextFieldCustom(
                value = amount.toString(),
                onValueChange = { amount = if(it.isNotEmpty()) it.toInt() else 0 },
                labelText = stringResource(id = R.string.amount_to_pay),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = if (isSessionCancelled) false else true
            )

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.use_agreement), fontSize = 18.sp)
                Checkbox(
                    checked = isUsingAgreement,
                    onCheckedChange = {yes ->
                        isUsingAgreement = yes
                        if(yes)
                            onClickUseAgreement.invoke()
                    },
                    enabled = if(isSessionCancelled) false else true
                    )
                Spacer(Modifier.padding(10.dp))
                if (isUsingAgreement)
                    Text(
                        text = stringResource(
                            id = R.string.remains_s,remainingAgreement.toString()),
                        fontSize = 18.sp
                    )
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.session_cancelled_by_patient),
                    fontSize = 18.sp
                )
                Checkbox(
                    checked = isSessionCancelled,
                    onCheckedChange = { isSessionCancelled = it
                        if (isSessionCancelled){
                            amount=0
                            isUsingAgreement = false
                        }
                    }
                )
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.payment_validated),
                    fontSize = 18.sp
                )
                Checkbox(
                    checked = isPaymentValidated,
                    onCheckedChange = { isPaymentValidated = it },
                    enabled = if(isSessionCancelled) false else true
                )
            }

            if(isProgressBarActive)
                CircularProgressIndicator()

            ButtonCustom(
                text = stringResource(id = R.string.save_session),
                modifier = Modifier.padding(vertical = 60.dp),onClick = {
                    onClickSaveSession.invoke(
                        CreateSessionDto(
                        sessionDate = dateSelected,
                        patientId = patientSelectedId,
                        paymentMethod = paymentMethodSelected,
                        amountDue = amount ?: 0,
                        isPaymentValidated = isPaymentValidated,
                        isSessionCancelled = isSessionCancelled,
                        isStateAgreementUsed = isUsingAgreement
                        )
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateSessionPreview(){

    PsyBudgetComposeTheme(darkTheme = false,dynamicColor = true) {
        CreateSessionView(
            true, emptyList(),{},{},0,0L,{})
    }

}