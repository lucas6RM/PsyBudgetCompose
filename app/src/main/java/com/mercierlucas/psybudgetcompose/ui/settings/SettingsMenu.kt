package com.mercierlucas.psybudgetcompose.ui.settings

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.navigation.Screen
import com.mercierlucas.psybudgetcompose.ui.custom.CardButtonMenu
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromSettingsMenuTo
import com.mercierlucas.psybudgetcompose.utils.DestinationsFromTransactionsMenuTo
import com.mercierlucas.psybudgetcompose.utils.theme.MyLightGrey
import com.mercierlucas.psybudgetcompose.utils.theme.MyRed
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun SettingsMenuScreen(navController: NavHostController, settingsMenuViewModel: SettingsMenuViewModel) {

    SettingsMenuView(
        destinationClicked = {destinationClicked ->
            with(navController){
                when(destinationClicked){
                    DestinationsFromSettingsMenuTo.MODIFY_PROFILE -> {}
                    DestinationsFromSettingsMenuTo.MODIFY_THEME -> {}
                    DestinationsFromSettingsMenuTo.LOGOUT -> {
                        settingsMenuViewModel.logout()
                        navigate(Screen.Login.route){
                            popUpTo(Screen.MainMenu.route){
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    )




}

@Composable
fun SettingsMenuView(
    destinationClicked:(DestinationsFromSettingsMenuTo)->Unit
){

    Column (Modifier.fillMaxSize()){

        HeaderCustom(title = stringResource(id = R.string.settings_menu))

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
                    text = stringResource(id = R.string.modify_profile),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromSettingsMenuTo.MODIFY_PROFILE)},
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
                    text = stringResource(id = R.string.modify_theme),
                    enableClick = false,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromSettingsMenuTo.MODIFY_THEME)},
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
                Spacer(Modifier.padding(10.dp))
            }

            Row(modifier = Modifier
                .weight(0.5F)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                CardButtonMenu(
                    containerColor = MyRed,
                    text = stringResource(id = R.string.logout),
                    enableClick = true,
                    onClick = {
                        destinationClicked.invoke(
                            DestinationsFromSettingsMenuTo.LOGOUT)},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview(){
    PsyBudgetComposeTheme (dynamicColor = false){
        SettingsMenuView({})
    }
}