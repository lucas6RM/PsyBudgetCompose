package com.mercierlucas.psybudgetcompose.ui.sessions.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.DropDownEnumString
import com.mercierlucas.psybudgetcompose.ui.custom.DropDownPatientsInTherapyList
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.custom.datepickers.DatePickerDocked
import com.mercierlucas.psybudgetcompose.utils.PaymentMethod
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun DetailsSessionScreen(
    navController: NavHostController,
    detailsSessionViewModel: DetailsSessionViewModel,
    idSessionSelected: Long
) {
    detailsSessionViewModel.getSessionById(idSessionSelected)

    val sessionToDisplay by detailsSessionViewModel.sessionIdentifiedStateFlow.collectAsState()

    val context = LocalContext.current

    if (sessionToDisplay != null)
        DetailsSessionView(
            sessionToDisplay = sessionToDisplay!!,
            onClickDeleteSession = {idSessionToDelete ->
                detailsSessionViewModel.deleteSession(idSessionToDelete)
            }
        )


    LaunchedEffect(true) {
        detailsSessionViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }

    LaunchedEffect(true) {
        detailsSessionViewModel.goToDailySessionsSharedFlow.collect { _ ->
            navController.popBackStack()
        }
    }

}

@Composable
fun DetailsSessionView(
    sessionToDisplay: SessionDto,
    onClickDeleteSession: (Long) -> Unit
) {

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        HeaderCustom(stringResource(id = R.string.session_details))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            OutlinedTextFieldCustom(
                value = sessionToDisplay.sessionDate,
                onValueChange = {},
                labelText = stringResource(id = R.string.session_date),
                enabled = false
            )

            OutlinedTextFieldCustom(
                value = "${sessionToDisplay.patient.firstName} ${sessionToDisplay.patient.lastName}",
                onValueChange = {},
                labelText = stringResource(id = R.string.patient),
                enabled = false
            )

            OutlinedTextFieldCustom(
                value = sessionToDisplay.transaction.paymentMethod,
                onValueChange = {},
                labelText = stringResource(id = R.string.payment_method),
                enabled = false
            )

            OutlinedTextFieldCustom(
                value = sessionToDisplay.transaction.amountDue.toString(),
                onValueChange = {},
                labelText = stringResource(id = R.string.amount_due),
                enabled = false
            )

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
                    checked = sessionToDisplay.transaction.isPaymentValidated,
                    onCheckedChange = { },
                    enabled = false
                )
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.agreement_used), fontSize = 18.sp)
                Checkbox(
                    checked = sessionToDisplay.isStateAgreementUsed,
                    onCheckedChange = { },
                    enabled = false
                )
            }


            ButtonCustom(
                text = stringResource(id = R.string.delete_session),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red),
                modifier = Modifier
                    .padding(vertical = 60.dp)
                ,onClick = {
                    onClickDeleteSession.invoke(sessionToDisplay.id)
                }
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun DetailsSessionPreview(){
    PsyBudgetComposeTheme {
       // DetailsSessionView(sessionToDisplay)
    }
}