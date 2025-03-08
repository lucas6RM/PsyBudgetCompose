package com.mercierlucas.psybudgetcompose.ui.mainmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom

@Composable
fun MainMenuScreen(navController: NavHostController, mainMenuViewModel: MainMenuViewModel) {

    MainMenuView()
}


@Composable
fun MainMenuView(){

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCustom("Main Menu")

    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    MainMenuView()

}