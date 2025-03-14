package com.mercierlucas.psybudgetcompose.ui.sessions.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.ui.custom.CardButtonMenu
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromSessionsMenuTo
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun MenuSessionScreen(navController: NavHostController) {
    MenuSessionView(destinationClicked = {destinationClicked ->
        with(navController){
            when(destinationClicked){
                DestinationsFromSessionsMenuTo.CREATE_SESSION ->
                    navigate(Screen.CreateSession.route)
                DestinationsFromSessionsMenuTo.DAILY_SESSIONS -> {}
                DestinationsFromSessionsMenuTo.SESSIONS_BY_PERIOD -> {}
                DestinationsFromSessionsMenuTo.SESSIONS_BY_PATIENTS -> {}
            }
        }


    })



}

@Composable
fun MenuSessionView(
    destinationClicked:(DestinationsFromSessionsMenuTo)->Unit){

    Column {

        HeaderCustom(title = stringResource(id = R.string.menu_session))
        Column {
            CardButtonMenu(
                text = stringResource(id = R.string.create_session),
                enableClick = true,
                onClick = {
                    destinationClicked.invoke(DestinationsFromSessionsMenuTo.CREATE_SESSION)}
            )
            CardButtonMenu(
                text = stringResource(id = R.string.daily_sessions),
                enableClick = true,
                onClick = {
                    destinationClicked.invoke(DestinationsFromSessionsMenuTo.DAILY_SESSIONS)}
            )
            CardButtonMenu(
                text = stringResource(id = R.string.sessions_by_period),
                enableClick = false,
                onClick = {
                    destinationClicked.invoke(DestinationsFromSessionsMenuTo.SESSIONS_BY_PERIOD)}
            )
            CardButtonMenu(
                text = stringResource(id = R.string.find_sessions_by_patient),
                enableClick = false,
                onClick = {
                    destinationClicked.invoke(DestinationsFromSessionsMenuTo.SESSIONS_BY_PATIENTS)
                }
            )
        }
    }

}

@Preview (showBackground = true)
@Composable
fun MenuSessionPreview(){
    PsyBudgetComposeTheme(dynamicColor = true) {
        MenuSessionView()
    }

}