package com.mercierlucas.psybudgetcompose.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.data.network.dtos.LoginDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.theme.MyBlue

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {

    val isProgressBarActive by loginViewModel.isProgressBarDisplayedStateFlow.collectAsState()

    val context = LocalContext.current


    LoginView(
        isProgressBarActive,
        onClickCreateNewAccount = { navController.navigate(Screen.Register.route) },
        onClickConfirmButton = { loginDto ->
            loginViewModel.validateInputs(loginDto)
        })

    LaunchedEffect(true) {
        loginViewModel.messageSharedFlow.collect { message ->
            context.showToast(message)
        }
    }

    LaunchedEffect(true) {
        loginViewModel.goToMainMenuSharedFlow.collect { yes ->
            if(yes)
                navController.navigate(Screen.MainMenu.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
        }
    }

}

@Composable
fun LoginView(
    isProgressBarActive: Boolean,
    onClickCreateNewAccount: () -> Unit,
    onClickConfirmButton: (LoginDto) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column {

        HeaderCustom("Login Page")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        )
        {
            OutlinedTextFieldCustom(
                value = email,
                onValueChange = { email = it },
                labelText = "Email (*)"
            )

            OutlinedTextFieldCustom(
                value = password,
                onValueChange = { password = it },
                labelText = "Password (*)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )


            ButtonCustom (
                onClick = {
                println("Email : $email \n Password : $password")
                onClickConfirmButton.invoke(LoginDto(email,password))
            })

            Row (){
                Text(text = "No Account ?")
                Spacer(modifier = Modifier.padding(horizontal = 20.dp))

                Text(
                    text = "Create an account",
                    color = MyBlue,
                    modifier = Modifier.clickable {
                        onClickCreateNewAccount.invoke()
                    }
                )
            }
            if (isProgressBarActive)
                CircularProgressIndicator()
        }
    }



}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginView(true, {}) {}
}