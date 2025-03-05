package com.mercierlucas.psybudgetcompose.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _emailStateFlow = MutableStateFlow("")
    private val _passwordStateFlow = MutableStateFlow("")
    val emailStateFlow = _emailStateFlow.asStateFlow()
    val passwordStateFlow = _passwordStateFlow.asStateFlow()

    private val _goToLogin = MutableSharedFlow<Boolean>()
    private val _goToMainMenu = MutableSharedFlow<Boolean>()
    val goToRegister = _goToLogin.asSharedFlow()
    val goToMainMenu = _goToMainMenu.asSharedFlow()


    fun setEmailStateFlow(email: String){
        _emailStateFlow.value = email
    }

    fun setPasswordStateFlow(password: String){
        _passwordStateFlow.value = password
    }

    fun goToRegisterScreen(){
        viewModelScope.launch {
            _goToLogin.emit(true)
        }
    }

    fun goToMainMenuScreen(){
        viewModelScope.launch {
            _goToMainMenu.emit(true)
        }
    }

}