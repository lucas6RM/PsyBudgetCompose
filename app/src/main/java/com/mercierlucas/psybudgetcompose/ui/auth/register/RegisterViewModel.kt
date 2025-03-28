package com.mercierlucas.psybudgetcompose.ui.auth.register

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.RegisterDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
): ViewModel() {

    private val _isProgressBarDisplayedStateFlow = MutableStateFlow(false)
    val isProgressBarDisplayedStateFlow = _isProgressBarDisplayedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<String>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _goToMainMenuSharedFlow = MutableSharedFlow<Boolean>()
    val goToMainMenuSharedFlow = _goToMainMenuSharedFlow.asSharedFlow()

    fun setIsProgressBarDisplayed(boolean: Boolean){
        _isProgressBarDisplayedStateFlow.value = boolean
    }


    fun displayToast(message:String){
        viewModelScope.launch{
            _messageSharedFlow.emit(message)
        }
    }

    fun goToMainMenuScreen(isResponseCorrectFromServer: Boolean){
        viewModelScope.launch {
            _goToMainMenuSharedFlow.emit(isResponseCorrectFromServer)
        }
    }

    private fun registerIn(registerDto: RegisterDto) {
        viewModelScope.launch {
            setIsProgressBarDisplayed(true)
            delay(1000)
            try {
                val responseRegister = withContext(Dispatchers.IO) {
                    apiService.registerUserThenLogInAndGetToken(registerDto)
                }

                val body = responseRegister?.body()
                when{
                    responseRegister == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseRegister.isSuccessful && body != null ->{
                        myPrefs.apply {
                            token = "Bearer ${body.token}"
                            userId = body.user.id
                            userFirstName = body.user.firstName
                            userLastName = body.user.lastName
                        }
                        displayToast("New user logged")
                        goToMainMenuScreen(true)
                    }
                    else -> {
                        when(responseRegister.code()){
                            400 -> displayToast("Email must be an email")
                            401 -> displayToast("Wrong password")
                            404 -> displayToast("User not found")
                            409 -> displayToast("User already exists")
                            else -> responseRegister.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast("Pas de connexion du serveur")
            }
            setIsProgressBarDisplayed(false)
        }
    }

    fun validateInputs(confirmedPassword: String, registerDto: RegisterDto) {
        with(registerDto){
            if (password == confirmedPassword)
                if (firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()
                    && email.isNotEmpty()){
                    registerIn(registerDto)
                }
                else
                    displayToast("Some required fields are empty")
            else
                displayToast("Confirmed password wrong")
        }
    }

}