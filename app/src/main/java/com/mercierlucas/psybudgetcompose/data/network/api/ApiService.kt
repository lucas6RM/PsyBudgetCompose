package com.mercierlucas.psybudgetcompose.data.network.api

import com.mercierlucas.psybudgetcompose.data.network.dtos.LoginDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.PatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.RegisterDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.AuthenticatedResponseDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreatePatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreateSessionDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.RespondeDeletePatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.ResponseSessionCreated
import com.mercierlucas.psybudgetcompose.data.network.dtos.ResponseUpdatePatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.UpdatePatientDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST(ApiRoutes.LOGIN_USER)
    suspend fun logUserInAndGetToken(
        @Body loginDto: LoginDto
    ) : Response<AuthenticatedResponseDto>?

    @POST(ApiRoutes.REGISTER_USER)
    suspend fun registerUserThenLogInAndGetToken(
        @Body registerDto: RegisterDto
    ) : Response<AuthenticatedResponseDto>?

    @GET(ApiRoutes.GET_ALL_PATIENTS)
    suspend fun getAllPatients(
        @Header("Authorization") token : String?,
    ) : Response<List<PatientDto>>?

    @POST(ApiRoutes.CREATE_PATIENT)
    suspend fun createPatient(
        @Header("Authorization") token : String?,
        @Body createPatientDto: CreatePatientDto
    ) : Response<PatientDto>?

    @GET(ApiRoutes.GET_PATIENT)
    suspend fun getPatient(
        @Header("Authorization") token : String?,
        @Path("id") id : Long,
    ) : Response<PatientDto>?

    @PATCH(ApiRoutes.UPDATE_PATIENT)
    suspend fun updatePatient(
        @Header("Authorization") token : String?,
        @Path("id") id : Long,
        @Body updatePatientDto: UpdatePatientDto,
    ) : Response<ResponseUpdatePatientDto>?


    @DELETE(ApiRoutes.DELETE_PATIENT)
    suspend fun deletePatient(
        @Header("Authorization") token : String?,
        @Path("id") id : Long,
    ) : Response<RespondeDeletePatientDto>?

    @GET(ApiRoutes.GET_REMAINING_AGREEMENTS)
    suspend fun getRemainingAgreementsForThisYear(
        @Header("Authorization") token : String?,
        @Query("patientId") patientId : Long,
    ) : Response<Int>?

    @POST(ApiRoutes.CREATE_SESSION)
    suspend fun createNewSession(
        @Header("Authorization") token : String?,
        @Body createSessionDto: CreateSessionDto
    ) : Response<ResponseSessionCreated>?



}