package com.mercierlucas.psybudgetcompose.data.network.api

import com.mercierlucas.psybudgetcompose.data.network.dtos.LoginDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.PatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.RegisterDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.AuthenticatedResponseDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreatePatientDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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

}