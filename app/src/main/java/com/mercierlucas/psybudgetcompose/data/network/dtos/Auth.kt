package com.mercierlucas.psybudgetcompose.data.network.dtos

import com.mercierlucas.psybudgetcompose.data.model.UserDto
import com.squareup.moshi.Json


data class RegisterDto(
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "postalCode")
    val postalCode: String?,
    @Json(name = "city")
    val city: String?,
)

data class LoginDto(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)

data class AuthenticatedResponseDto(
    @Json(name = "token")
    val token: String,
    @Json(name = "user")
    val user: UserDto
)