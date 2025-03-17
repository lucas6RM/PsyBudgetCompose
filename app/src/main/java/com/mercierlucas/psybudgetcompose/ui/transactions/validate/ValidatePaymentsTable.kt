package com.mercierlucas.psybudgetcompose.ui.transactions.validate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.network.dtos.SessionDueThisYear
import com.mercierlucas.psybudgetcompose.ui.custom.SplitLine
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun ValidatePaymentsTable(sessionsDueThisYear: List<SessionDueThisYear>, checkBoxIsChecked: Boolean) {

    Column(modifier = Modifier.fillMaxSize()) {

        TableHeader()
        TableContent(
            sessionsDueThisYear = sessionsDueThisYear
        )

    }
}


@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
        //horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.session_n_date),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(id = R.string.patient_n_name),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(id = R.string.payment_n_method),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.amount_n_due),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(modifier = Modifier.weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.ok_question),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 5.dp),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

    }
    SplitLine()
}

@Composable
fun TableContent(
    modifier: Modifier = Modifier,
    sessionsDueThisYear: List<SessionDueThisYear>) {

    var isPopupDisplayed by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

            .padding(vertical = 8.dp),
    ) {
        items(sessionsDueThisYear) { session ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier=Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = session.sessionDate,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier=Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = session.patient.firstName,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = session.patient.lastName,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier=Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = session.transaction.paymentMethod,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier=Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = session.transaction.amountDue.toString(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }



                Column(modifier=Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = session.transaction.isPaymentValidated,
                        onCheckedChange = {
                            isPopupDisplayed = true
                        }
                    )
                }


            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewScrollableTable(
    modifier: Modifier = Modifier
) {

    val data = List(25) { index ->
        listOf(
            "2025-02-22",
            "FirstName $index\nLastname",
            "Tiers\npayant",
            "${index%10 * 5 + 20}",
        )
    }

    PsyBudgetComposeTheme (darkTheme = false,dynamicColor = false){
        //ValidatePaymentsTable(sessionsDueThisYear)
    }

}