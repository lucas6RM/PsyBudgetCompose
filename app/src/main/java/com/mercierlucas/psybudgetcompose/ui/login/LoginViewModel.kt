package com.mercierlucas.psybudgetcompose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _emailStateFlow = MutableStateFlow("")
    private val _passwordStateFlow = MutableStateFlow("")
    val emailStateFlow = _emailStateFlow.asStateFlow()
    val passwordStateFlow = _passwordStateFlow.asStateFlow()

    private val _goToRegister = MutableSharedFlow<Boolean>()
    private val _goToMainMenu = MutableSharedFlow<Boolean>()
    val goToRegister = _goToRegister.asSharedFlow()
    val goToMainMenu = _goToMainMenu.asSharedFlow()


    fun setEmailStateFlow(email: String){
        _emailStateFlow.value = email
    }

    fun setPasswordStateFlow(password: String){
        _passwordStateFlow.value = password
    }

    fun goToRegisterScreen(){
        viewModelScope.launch {
            _goToRegister.emit(true)
        }
    }

    fun goToMainMenuScreen(){
        viewModelScope.launch {
            _goToMainMenu.emit(true)
        }
    }

}