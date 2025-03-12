package com.mercierlucas.psybudgetcompose.ui.patients.one

import android.content.ContentValues
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.PatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.UpdatePatientDto
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
class PatientByIdViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {

    private val _patientSelectedStateFlow = MutableStateFlow<PatientDto?>(null)
    val patientSelectedStateFlow = _patientSelectedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _isModifyActivatedStateFlow = MutableStateFlow(false)
    val isModifyActivatedStateFlow = _isModifyActivatedStateFlow.asStateFlow()

    private val _goToAllPatientsScreenSharedFlow = MutableSharedFlow<Boolean>()
    val goToAllPatientScreenSharedFlow = _goToAllPatientsScreenSharedFlow.asSharedFlow()

    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }

    private fun goToAllPatientsScreen() {
        viewModelScope.launch {
            _goToAllPatientsScreenSharedFlow.emit(true)
        }
    }

    fun setPatientInfos(idPatientSelected: Long) {
        viewModelScope.launch {
            try {
                val responseGetPatient = withContext(Dispatchers.IO){
                    apiService.getPatient(myPrefs.token,idPatientSelected)
                }
                val body = responseGetPatient?.body()
                when{
                    responseGetPatient == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetPatient.isSuccessful && body !=null ->{
                        _patientSelectedStateFlow.value = body
                        displayToast(R.string.patient_identified)
                    }
                    else -> {
                        when(responseGetPatient.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.patient_not_found)
                            else -> responseGetPatient.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
        }
    }

    fun validateInputs(patientToUpdate: UpdatePatientDto) {
        with(patientToUpdate){
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()){
                updatePatientById(patientToUpdate.id, patientToUpdate)
            }
            else
                displayToast(R.string.some_required_fields_are_empty)
        }


    }

    private fun updatePatientById(idPatientToUpdate: Long, patientToUpdate: UpdatePatientDto) {
        viewModelScope.launch {
            try {
                val responseUpdatePatient = withContext(Dispatchers.IO){
                    apiService.updatePatient(myPrefs.token,idPatientToUpdate,patientToUpdate)
                }
                val body = responseUpdatePatient?.body()
                when{
                    responseUpdatePatient == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseUpdatePatient.isSuccessful && body !=null ->{
                        displayToast(R.string.patient_modified)
                        goToAllPatientsScreen()
                    }
                    else -> {
                        when(responseUpdatePatient.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.patient_not_found)
                            else -> responseUpdatePatient.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
        }

    }

    fun deletePatientById(idPatientToDelete: Long) {
        viewModelScope.launch {
            try {
                val responseDeletePatient = withContext(Dispatchers.IO){
                    apiService.deletePatient(myPrefs.token,idPatientToDelete)
                }
                val body = responseDeletePatient?.body()
                when{
                    responseDeletePatient == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseDeletePatient.isSuccessful && body !=null ->{
                        displayToast(R.string.patient_deleted)
                        goToAllPatientsScreen()
                    }
                    else -> {
                        when(responseDeletePatient.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.patient_not_found)
                            else -> responseDeletePatient.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
        }
    }


}