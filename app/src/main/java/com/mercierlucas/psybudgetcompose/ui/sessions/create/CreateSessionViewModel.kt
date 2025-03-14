package com.mercierlucas.psybudgetcompose.ui.sessions.create

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreateSessionDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.PatientDto
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
class CreateSessionViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {

    private val _isProgressBarDisplayedStateFlow = MutableStateFlow(false)
    val isProgressBarDisplayedStateFlow = _isProgressBarDisplayedStateFlow.asStateFlow()

    private val _messageSharedFlow = MutableSharedFlow<Int>()
    val messageSharedFlow = _messageSharedFlow.asSharedFlow()

    private val _activePatientLiteListStateFlow = MutableStateFlow<List<PatientLite>>(emptyList())
    val activePatientLiteListStateFlow = _activePatientLiteListStateFlow.asStateFlow()

    private val _patientSelectedStateFlow = MutableStateFlow<PatientLite?>(null)
    val patientSelectedStateFlow = _patientSelectedStateFlow.asStateFlow()

    private val _remainingAgreementsStateFlow = MutableStateFlow(12)
    val remainingAgreementsStateFlow = _remainingAgreementsStateFlow.asStateFlow()


    fun setIsProgressBarDisplayed(boolean: Boolean){
        _isProgressBarDisplayedStateFlow.value = boolean
    }

    init {
        getAllActivePatientsLite()
    }

    private fun displayToast(stringId:Int){
        viewModelScope.launch{
            _messageSharedFlow.emit(stringId)
        }
    }

    fun getAllActivePatientsLite() {
        viewModelScope.launch {
            try {
                val responseGetAllPatients = withContext(Dispatchers.IO){
                    apiService.getAllPatients(myPrefs.token)
                }
                val body = responseGetAllPatients?.body()
                when{
                    responseGetAllPatients == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetAllPatients.isSuccessful && body !=null ->{
                        _activePatientLiteListStateFlow.value = body
                            .toList()
                            .filter { patientDto -> patientDto.isActive }
                            .map { patientDto ->
                                PatientLite(
                                    id = patientDto.id,
                                    firstName = patientDto.firstName,
                                    lastName = patientDto.lastName,
                                    isActive = patientDto.isActive
                                )
                            }
                    }
                    else -> {
                        when(responseGetAllPatients.code()){
                            404 -> displayToast(R.string.user_not_found)
                            else -> responseGetAllPatients.errorBody()?.let {
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


    fun getRemainingAgreementsForThisYear() {
        viewModelScope.launch {
            try {
                val responseGetRemainingAgreement = withContext(Dispatchers.IO) {
                    patientSelectedStateFlow.value?.let {patient ->
                        apiService.getRemainingAgreementsForThisYear(
                            myPrefs.token, patient.id)
                    }
                }
                val body = responseGetRemainingAgreement?.body()
                when{
                    responseGetRemainingAgreement == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseGetRemainingAgreement.isSuccessful && body != null ->{
                        _remainingAgreementsStateFlow.value = body
                    }
                    else -> {
                        when(responseGetRemainingAgreement.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.patient_not_found)
                            else -> responseGetRemainingAgreement.errorBody()?.let {
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

    fun setCurrentPatientSelected(patient: PatientLite) {
        _patientSelectedStateFlow.value = patient
    }

    fun validateInputsBeforeSaving(createSessionDto: CreateSessionDto) {
        with(createSessionDto){
            if (sessionDate.isNotEmpty() && patientId!=0L )
                saveSession(createSessionDto)
            else
                displayToast(R.string.some_required_fields_are_empty)
        }
    }

    private fun saveSession(createSessionDto: CreateSessionDto) {
        viewModelScope.launch {
            setIsProgressBarDisplayed(true)
            delay(1000)
            try {
                val responseSaveSession = withContext(Dispatchers.IO) {
                    apiService.createNewSession(myPrefs.token,createSessionDto)
                }
                val body = responseSaveSession?.body()
                when{
                    responseSaveSession == null -> Log.e(ContentValues.TAG,"Pas de reponse du serveur")
                    responseSaveSession.isSuccessful && body != null ->{
                        displayToast(R.string.new_session_created)
                    }
                    else -> {
                        when(responseSaveSession.code()){
                            400 -> displayToast(R.string.bad_request)
                            401 -> displayToast(R.string.unauthorized)
                            404 -> displayToast(R.string.patient_not_found)
                            else -> responseSaveSession.errorBody()?.let {
                                Log.e(ContentValues.TAG, it.string())
                            }
                        }
                    }
                }
            } catch (error : ConnectException){
                displayToast(R.string.nothing_from_server)
            }
            setIsProgressBarDisplayed(false)
        }

    }


}