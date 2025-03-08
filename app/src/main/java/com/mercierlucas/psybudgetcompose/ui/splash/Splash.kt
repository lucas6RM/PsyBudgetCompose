package com.mercierlucas.psybudgetcompose.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.ui.navigation.Screen


@Composable
fun SplashScreen(navController: NavHostController, splashViewModel: SplashViewModel) {

    SplashView()

    LaunchedEffect(true) {
        splashViewModel.isUserLoggedSharedFlow.collect{ yes ->
            if(yes)
                navController.navigate(Screen.MainMenu.route){
                    popUpTo(Screen.Splash.route){
                        inclusive = true
                    }
                }
            else
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Splash.route){
                        inclusive = true
                    }
                }
        }
    }
}

@Composable
fun SplashView(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.nintendologo),
            contentDescription = "splash logo",
            modifier = Modifier.size(300.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashView()
}