package com.mercierlucas.psybudgetcompose.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.theme.MyBlue

@Composable
fun LoginScreen(){
    val viewModel: LoginViewModel = viewModel()
    val email by viewModel.emailStateFlow.collectAsState()
    val password by viewModel.passwordStateFlow.collectAsState()

    LoginView(
        email,
        password,
        { viewModel.setEmailStateFlow(it) },
        { viewModel.setPasswordStateFlow(it) },
        { viewModel.goToRegisterScreen() },
        { viewModel.goToMainMenuScreen() }
    )

    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.goToRegister.collect {
            if(it)
                Toast.makeText(context, "Aller vers register", Toast.LENGTH_LONG).show() }
    }

    LaunchedEffect(true) {
        viewModel.goToMainMenu.collect {
            if(it)
                Toast.makeText(context, "Aller vers main menu", Toast.LENGTH_LONG).show()
        }
    }

}

@Composable
fun LoginView(
    email: String,
    password: String,
    emailSetter: (String) -> Unit,
    passwordSetter: (String) -> Unit,
    goToRegisterScreen: () -> Unit,
    goToMainMenuScreen: () -> Unit
) {

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


        OutlinedTextField(
            value = email,
            onValueChange = emailSetter,
            label = { Text(text = "Enter an email")})

        OutlinedTextField(
            value = password,
            onValueChange = passwordSetter,
            label = { Text(text = "Enter a password")},
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true)


        ButtonCustom(onClick = {
            println("Email : $email \n Password : $password")
            goToMainMenuScreen.invoke()
        })

        Row (){
            Text(text = "No Account ?")
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))

            Text(
                text = "Create an account",
                color = MyBlue,
                modifier = Modifier.clickable { goToRegisterScreen.invoke()}
            )
        }
    }
}



}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}