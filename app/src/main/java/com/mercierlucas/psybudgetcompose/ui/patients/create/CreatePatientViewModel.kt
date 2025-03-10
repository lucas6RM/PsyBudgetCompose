package com.mercierlucas.psybudgetcompose.ui.patients.create

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreatePatientDto
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
class CreatePatientViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {

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

    fun validateInputs(createPatientDto: CreatePatientDto) {
        with(createPatientDto){
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()){
                addNewPatient(createPatientDto)
            }
            else
                displayToast("Some required fields are empty")
        }
    }

    private fun addNewPatient(createPatientDto: CreatePatientDto) {
        viewModelScope.launch {
            setIsProgressBarDisplayed(true)
            delay(1000)
            try {
                val responseCreatePatient = withContext(Dispatchers.IO) {
                    apiService.createPatient(myPrefs.token,createPatientDto)
                }

                val body = responseCreatePatient?.body()
                when{
                    responseCreatePatient == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseCreatePatient.isSuccessful && body != null ->{
                        displayToast("New patient added : ${body.firstName} ${body.lastName}")
                        goToMainMenuScreen(true)
                    }
                    else -> {
                        when(responseCreatePatient.code()){
                            400 -> displayToast("Phone must be a phone number")
                            404 -> displayToast("User not found")
                            else -> responseCreatePatient.errorBody()?.let {
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
}