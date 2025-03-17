package com.mercierlucas.psybudgetcompose.ui.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.ui.custom.CardButtonMenu
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromMainMenuTo
import com.mercierlucas.psybudgetcompose.utils.theme.MyBlue


@Composable
fun MainMenuScreen(navController: NavHostController, mainMenuViewModel: MainMenuViewModel) {

    val userFirstName by mainMenuViewModel.userFirstNameStateFlow.collectAsState()

    MainMenuView(userFirstName) { destination ->
        with(navController){
            when (destination) {
                DestinationsFromMainMenuTo.PATIENTS ->
                    navigate(Screen.AllPatients.route)

                DestinationsFromMainMenuTo.SESSIONS ->
                    navigate(Screen.SessionMenu.route)

                DestinationsFromMainMenuTo.CREATE_SESSION ->
                    navigate(Screen.CreateSession.route)

                DestinationsFromMainMenuTo.TRANSACTIONS ->
                    navigate(Screen.TransactionsMenu.route)

                DestinationsFromMainMenuTo.PAID_INVOICES -> {}

                DestinationsFromMainMenuTo.STATISTICS -> {}

                DestinationsFromMainMenuTo.SETTINGS -> {}

            }
        }
    }
}

@Composable
fun MainMenuView(
    userFirstName: String?,
    destinationClicked:(DestinationsFromMainMenuTo)->Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderCustom("Main Menu")
        WelcomeBanner(userFirstName = userFirstName,destinationClicked)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ){
                CardButtonMenu(text = "Quick Session Recording",
                    onClick = { destinationClicked
                        .invoke(DestinationsFromMainMenuTo.CREATE_SESSION) })
            }

            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier
                    .weight(1F)) {
                    CardButtonMenu(
                        text = "Patients",
                        onClick = { destinationClicked
                            .invoke(DestinationsFromMainMenuTo.PATIENTS) })
                }
                Column(modifier = Modifier
                    .weight(1F)) {
                    CardButtonMenu(
                        text = "Sessions",
                        onClick = { destinationClicked
                            .invoke(DestinationsFromMainMenuTo.SESSIONS) })
                }
            }

            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Column(modifier = Modifier
                    .weight(1F)) {
                    CardButtonMenu(
                        text = "Transactions",
                        onClick = { destinationClicked
                            .invoke(DestinationsFromMainMenuTo.TRANSACTIONS) }
                    )
                }
                Column(modifier = Modifier
                    .weight(1F)) {
                    CardButtonMenu(text = "PaidInvoices",enableClick = false,
                        onClick = { destinationClicked
                                .invoke(DestinationsFromMainMenuTo.PAID_INVOICES) }
                    )
                }
            }

            Row(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Center){
                CardButtonMenu(text = "Statistics",enableClick = false,
                    onClick = { destinationClicked
                        .invoke(DestinationsFromMainMenuTo.STATISTICS) })
            }
        }
    }
}

@Composable
fun WelcomeBanner(userFirstName: String?,destinationClicked:(DestinationsFromMainMenuTo)->Unit){
    Row(
        modifier = Modifier.background(MyBlue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = "Bonjour $userFirstName !",
                fontSize = 25.sp,
                color = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { destinationClicked.invoke(DestinationsFromMainMenuTo.SETTINGS) },
                tint = Color.White
            )
        }
    }
    Row(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(1.dp)
    ) {}
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    MainMenuView("Julia", {})

}