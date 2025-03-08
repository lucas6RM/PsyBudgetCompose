package com.mercierlucas.psybudgetcompose.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.mercierlucas.psybudgetcompose.data.network.requests.dtos.RegisterDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.navigation.Screen

@Composable
fun RegisterScreen(navController: NavHostController, registerViewModel: RegisterViewModel) {

    val isProgressBarActive by registerViewModel.isProgressBarDisplayedStateFlow.collectAsState()

    val context = LocalContext.current

    RegisterView(isProgressBarActive,
        onClickConfirmButton = { confirmedPassword,registerDto ->
            registerViewModel.validateInputs(confirmedPassword,registerDto)
        }
    )

    LaunchedEffect(true) {
        registerViewModel.messageSharedFlow.collect { message ->
            context.showToast(message)
        }
    }

    LaunchedEffect(true) {
        registerViewModel.goToMainMenuSharedFlow.collect { yes ->
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
fun RegisterView(
    isProgressBarActive: Boolean,
    onClickConfirmButton: (String,RegisterDto) -> Unit
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)  {

        HeaderCustom("Register Page")

        Column(
            Modifier
                .padding(top = 50.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ){
            OutlinedTextFieldCustom(
                value = firstName,
                onValueChange = { firstName = it },
                labelText = "First name (*)"
            )
            OutlinedTextFieldCustom(
                value = lastName,
                onValueChange = { lastName = it },
                labelText = "Last name (*)"
            )
            OutlinedTextFieldCustom(
                value = email ,
                onValueChange = { email = it },
                labelText = "Email (*)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextFieldCustom(
                value = password ,
                onValueChange = { password = it },
                labelText = "Password (*)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextFieldCustom(
                value = confirmedPassword ,
                onValueChange = { confirmedPassword = it },
                labelText = "Confirm your password (*)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextFieldCustom(
                value = phone ,
                onValueChange = { phone = it },
                labelText = "Phone number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            OutlinedTextFieldCustom(
                value = address ,
                onValueChange = {  address = it },
                labelText = "Address"
            )
            OutlinedTextFieldCustom(
                value = postalCode ,
                onValueChange = {  postalCode = it },
                labelText = "Postal code",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextFieldCustom(
                value = city ,
                onValueChange = {  city = it },
                labelText = "City"
            )
        }

        if (isProgressBarActive)
            CircularProgressIndicator()

        ButtonCustom(modifier = Modifier.padding(vertical = 60.dp)) {
            onClickConfirmButton.invoke(
                confirmedPassword,
                RegisterDto(firstName,lastName,password,email,phone,address,postalCode,city)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterView (true){_,_ -> }
}