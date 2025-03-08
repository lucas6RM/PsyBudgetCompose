package com.mercierlucas.psybudgetcompose.data.network.api

import com.mercierlucas.psybudgetcompose.data.entities.PatientDto
import com.mercierlucas.psybudgetcompose.data.network.requests.dtos.LoginDto
import com.mercierlucas.psybudgetcompose.data.network.requests.dtos.RegisterDto
import com.mercierlucas.psybudgetcompose.data.network.responses.dtos.AuthenticatedResponseDto
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

}