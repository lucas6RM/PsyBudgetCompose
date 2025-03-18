package com.mercierlucas.psybudgetcompose.ui.transactions.validate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDueThisYear
import com.mercierlucas.psybudgetcompose.ui.custom.ConfirmActionDialog
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.SplitLine
import com.mercierlucas.psybudgetcompose.ui.custom.datepickers.DatePickerDocked
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme
import kotlinx.coroutines.delay

@Composable
fun ValidatePaymentsScreen(
    navController: NavHostController,
    validatePaymentsViewModel: ValidatePaymentsViewModel
) {
    val isProgressBarActive by validatePaymentsViewModel.isProgressBarDisplayedStateFlow
        .collectAsState()
    val sessionsDueThisYear by validatePaymentsViewModel.sessionsDueThisYearStateFlow
        .collectAsState()


    val context = LocalContext.current

    ValidatePaymentsView(onDateSelected = {selectedDate ->
        validatePaymentsViewModel.getAllSessionsDueThisYear(selectedDate)
    },
        sessionsDueThisYear = sessionsDueThisYear,
        onValidatePaymentConfirm = {session ->
          validatePaymentsViewModel.updatePaymentValidated(session)
        },
        isProgressBarActive,
    )


    LaunchedEffect(true) {
        validatePaymentsViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }


}

@Composable
fun ValidatePaymentsView(
    onDateSelected: (String) -> Unit,
    sessionsDueThisYear: List<SessionDueThisYear>,
    onValidatePaymentConfirm: (SessionDueThisYear) -> Unit,
    isProgressBarActive: Boolean,
){

    var sessionToValidatePayment by remember {
        mutableStateOf<SessionDueThisYear?>(null)
    }

    var isPopupDisplayed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        ){
        Column {
            HeaderCustom(title = stringResource(id = R.string.validate_payments))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerDocked(
                    modifier = Modifier.padding(vertical = 10.dp),
                    textLabel = stringResource(id = R.string.select_year),
                    onDateSelected = { selectedDate ->
                        if (selectedDate != null) {
                            onDateSelected.invoke(selectedDate)
                        }
                    }
                )
            }
            SplitLine()
            ValidatePaymentsTable(
                sessionsDueThisYear,
                onClickOnCheckbox = { sessionDueThisYear ->
                    sessionToValidatePayment = sessionDueThisYear
                    isPopupDisplayed = true
                },
                isPopupDisplayed
                )
        }
        if (isPopupDisplayed)
            ConfirmActionDialog(
                onDismissRequest = { isPopupDisplayed = false},
                onConfirmation = {
                    sessionToValidatePayment?.let { onValidatePaymentConfirm.invoke(it) }
                    isPopupDisplayed = false
                },
                dialogTitle = stringResource(id = R.string.are_you_sure),
                dialogText = stringResource(id = R.string.confirm_payment_validation),
                icon = Icons.Rounded.WarningAmber
            )

        if(isProgressBarActive){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ValidatePaymentsPreview(){
    PsyBudgetComposeTheme (dynamicColor = false){
        //ValidatePaymentsView({}, sessionsDueThisYear)
    }
}
