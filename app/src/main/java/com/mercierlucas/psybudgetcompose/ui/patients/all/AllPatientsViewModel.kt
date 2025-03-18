package com.mercierlucas.psybudgetcompose.ui.patients.all

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AllPatientsViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
)
    : ViewModel(){

    private val _patientLiteListStateFlow = MutableStateFlow<List<PatientLite>>(emptyList())
    val patientLiteListStateFlow = _patientLiteListStateFlow.asStateFlow()

    private val _patientLiteListFilteredStateFlow = MutableStateFlow<List<PatientLite>>(emptyList())
    val patientLiteListFilteredStateFlow = _patientLiteListFilteredStateFlow.asStateFlow()

    private val _isFilterCheckedStateFlow = MutableStateFlow<Boolean>(false)
    val isFilterCheckedStateFlow = _isFilterCheckedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<String>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()


    fun setIsFilterChecked(checkedChanged: Boolean){
        _isFilterCheckedStateFlow.value = checkedChanged
        refreshFilteredPatients(checkedChanged)
    }

    private fun displayToast(message:String){
        viewModelScope.launch{
            _messageSharedFlow.emit(message)
        }
    }

    private fun refreshFilteredPatients(checkedChanged: Boolean) {
        if (checkedChanged)
            _patientLiteListFilteredStateFlow.value =
                _patientLiteListStateFlow.value.filter { patient ->
                    patient.isActive
                }
        else
            _patientLiteListFilteredStateFlow.value = _patientLiteListStateFlow.value
    }

    fun getAllPatientsAndRefreshFilter() {
        viewModelScope.launch {
            try {
                val responseGetAllPatients = withContext(Dispatchers.IO){
                    apiService.getAllPatients(myPrefs.token)
                }
                val body = responseGetAllPatients?.body()
                when{
                    responseGetAllPatients == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetAllPatients.isSuccessful && body !=null ->{
                        _patientLiteListStateFlow.value = body.toList().map { patientDto ->
                            PatientLite(
                                id = patientDto.id,
                                firstName = patientDto.firstName,
                                lastName = patientDto.lastName,
                                isActive = patientDto.isActive
                                )
                        }
                        refreshFilteredPatients(_isFilterCheckedStateFlow.value)
                    }
                    else -> {
                        when(responseGetAllPatients.code()){

                            404 -> displayToast("User not found")
                            else -> responseGetAllPatients.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast("Pas de connexion du serveur")
            }
        }
    }


}