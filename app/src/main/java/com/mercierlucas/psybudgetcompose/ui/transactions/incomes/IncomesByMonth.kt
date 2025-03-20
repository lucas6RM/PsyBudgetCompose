package com.mercierlucas.psybudgetcompose.ui.transactions.incomes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.PaymentStats
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.SplitLine
import com.mercierlucas.psybudgetcompose.ui.custom.datepickers.DatePickerDocked
import com.mercierlucas.psybudgetcompose.utils.theme.MyDarkGreen
import com.mercierlucas.psybudgetcompose.utils.theme.MyPink
import com.mercierlucas.psybudgetcompose.utils.theme.MyPurple
import com.mercierlucas.psybudgetcompose.utils.theme.MyYellow
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun IncomesByMonthScreen(
    navController: NavHostController,
    incomesByMonthViewModel: IncomesByMonthViewModel
) {

    val paymentStats by incomesByMonthViewModel.paymentStatsStateFlow.collectAsState()


    val context = LocalContext.current

    IncomesByMonthView(
        onDateSelected = { strDateFormatted ->
            incomesByMonthViewModel.getAllSessionsThisMonth(strDateFormatted)
        },
        paymentStats
    )

    LaunchedEffect(true) {
        incomesByMonthViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }

}

@Composable
fun IncomesByMonthView(
    onDateSelected: (String) -> Unit,
    paymentStats: PaymentStats?,
){


    Column(Modifier.fillMaxSize()) {
        HeaderCustom(title = stringResource(id = R.string.incomes_by_month))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePickerDocked(
                modifier = Modifier.padding(vertical = 10.dp),
                textLabel = stringResource(id = R.string.select_month),
                onDateSelected = { selectedDate ->
                    if (selectedDate != null) {
                        onDateSelected.invoke(selectedDate)
                    }
                }
            )
        }
        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .weight(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyPurple)){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){

                    Text(
                        stringResource(id = R.string.number_of_agreements_used),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelSmall)


                    Text(
                        paymentStats?.numberAgreementUsed.toString(),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .background(Color.White)
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall)
                }



            }

        }

        SplitLine()


        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyPink)){

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(id = R.string.credit_card),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.creditCardSumPaid.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)

                        Spacer(Modifier.padding(10.dp))

                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )
                        Text(
                            paymentStats?.creditCardSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }

                }
            }

        }

        SplitLine()


        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyPink)){

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(id = R.string.bank_check),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.bankCheckSumPaid.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)

                        Spacer(Modifier.padding(10.dp))

                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.bankCheckSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }

                }
            }

        }
        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyPink)
                ){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(id = R.string.bank_transfer),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.bankTransferSumPaid.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)

                        Spacer(Modifier.padding(10.dp))


                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.bankTransferSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }

                }
            }

        }
        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyPink)){

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stringResource(id = R.string.cash),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.cashSumPaid.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)

                        Spacer(Modifier.padding(10.dp))


                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.cashSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }


                }
            }

        }

        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyYellow)){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = stringResource(id = R.string.third_party_payer),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium
                    )




                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.thirdPartyPayerSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)

                        Spacer(Modifier.padding(10.dp))

                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.thirdPartyPayerSumDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }

                }
            }

        }

        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MyDarkGreen)){

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = stringResource(id = R.string.total_incomes),
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp),
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelMedium)

                    Row {

                        Text(
                            text = stringResource(id = R.string.paid),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(
                            paymentStats?.totalPaid.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.padding(10.dp))


                        Text(
                            text = stringResource(id = R.string.due),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(),
                        )

                        Text(

                            paymentStats?.totalDue.toString(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall)
                    }



                }

            }

        }



    }






}

@Preview(showBackground = true)
@Composable
fun IncomesByMonthPreview(){
    PsyBudgetComposeTheme {
        IncomesByMonthView({}, null)
    }
}
