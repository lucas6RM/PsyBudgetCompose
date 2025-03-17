package com.mercierlucas.psybudgetcompose.ui.transactions.incomes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun IncomesByMonthScreen(
    navController: NavHostController,
    incomesByMonthViewModel: IncomesByMonthViewModel
) {
    IncomesByMonthView()
}

@Composable
fun IncomesByMonthView(){

    Column(Modifier.fillMaxSize()) {
        HeaderCustom(title = stringResource(id = R.string.incomes_by_month))
    }

}

@Preview(showBackground = true)
@Composable
fun IncomesByMonthPreview(){
    PsyBudgetComposeTheme {
        IncomesByMonthView()
    }
}
