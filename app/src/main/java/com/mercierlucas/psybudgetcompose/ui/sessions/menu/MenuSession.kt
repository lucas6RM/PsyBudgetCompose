package com.mercierlucas.psybudgetcompose.ui.sessions.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromMainMenuTo
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromSessionsMenuTo
import com.mercierlucas.psybudgetcompose.utils.theme.MyLightGrey
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun MenuSessionScreen(navController: NavHostController) {
    MenuSessionView(destinationClicked = {destinationClicked ->
        with(navController){
            when(destinationClicked){
                DestinationsFromSessionsMenuTo.CREATE_SESSION ->
                    navigate(Screen.CreateSession.route)
                DestinationsFromSessionsMenuTo.DAILY_SESSIONS ->
                    navigate(Screen.DailySessions.route)
                DestinationsFromSessionsMenuTo.SESSIONS_BY_PERIOD -> {}
                DestinationsFromSessionsMenuTo.SESSIONS_BY_PATIENTS -> {}
            }
        }


    })



}

@Composable
fun MenuSessionView(
    destinationClicked:(DestinationsFromSessionsMenuTo)->Unit){

    Column (Modifier.fillMaxSize()){

        HeaderCustom(title = stringResource(id = R.string.menu_session))

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
                    text = stringResource(id = R.string.create_session),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(DestinationsFromSessionsMenuTo.CREATE_SESSION)}
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
                    text = stringResource(id = R.string.daily_sessions),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(DestinationsFromSessionsMenuTo.DAILY_SESSIONS)}
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
                    text = stringResource(id = R.string.sessions_by_period),
                    enableClick = false,
                    onClick = {
                        destinationClicked.invoke(DestinationsFromSessionsMenuTo.SESSIONS_BY_PERIOD)},
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
                    text = stringResource(id = R.string.find_sessions_by_patient),
                    enableClick = false,
                    onClick = {
                        destinationClicked.invoke(DestinationsFromSessionsMenuTo.SESSIONS_BY_PATIENTS)
                    },
                    containerColor = MyLightGrey
                )

            }
















        }
    }

}

@Preview (showBackground = true)
@Composable
fun MenuSessionPreview(){
    PsyBudgetComposeTheme(dynamicColor = false) {
        MenuSessionView({})
    }

}