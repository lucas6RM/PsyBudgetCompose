package com.mercierlucas.psybudgetcompose.ui.transactions.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.ui.custom.CardButtonMenu
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromTransactionsMenuTo
import com.mercierlucas.psybudgetcompose.utils.theme.MyLightGrey
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun MenuTransactionScreen(navController: NavHostController) {
    MenuTransactionView(
        destinationClicked = {destinationClicked ->
            with(navController){
                when(destinationClicked){
                    DestinationsFromTransactionsMenuTo.INCOMES_BY_MONTH ->
                        navigate(Screen.IncomesByMonth.route)

                    DestinationsFromTransactionsMenuTo.VALIDATE_PAYMENTS ->
                        navigate(Screen.ValidatePayments.route)

                    DestinationsFromTransactionsMenuTo.TRANSACTIONS_BY_PATIENTS -> {}
                }
            }
        }
    )



}

@Composable
fun MenuTransactionView(
    destinationClicked:(DestinationsFromTransactionsMenuTo)->Unit){

    Column (Modifier.fillMaxSize()){

        HeaderCustom(title = stringResource(id = R.string.menu_transactions))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {

            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                CardButtonMenu(
                    text = stringResource(id = R.string.incomes_by_month),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromTransactionsMenuTo.INCOMES_BY_MONTH)}
                )
            }


            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                CardButtonMenu(
                    text = stringResource(id = R.string.search_transactions_by_patients),
                    enableClick = false,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromTransactionsMenuTo.TRANSACTIONS_BY_PATIENTS)},
                    containerColor = MyLightGrey
                )

            }


            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                CardButtonMenu(
                    text = stringResource(id = R.string.validate_payments),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromTransactionsMenuTo.VALIDATE_PAYMENTS)}
                )

            }
            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.padding(10.dp))
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun MenuTransactionPreview(){
    PsyBudgetComposeTheme(dynamicColor = false) {
        MenuTransactionView({})
    }

}