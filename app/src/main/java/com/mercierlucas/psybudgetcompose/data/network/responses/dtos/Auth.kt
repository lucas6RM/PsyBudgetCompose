package com.mercierlucas.psybudgetcompose.data.network.responses.dtos

import android.os.Parcelable
import com.mercierlucas.psybudgetcompose.data.entities.UserDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class AuthenticatedResponseDto(
    @Json(name = "token")
    val token: String,
    @Json(name = "user")
    val user: UserDto
)

@Parcelize
data class ErrorResponseDto(
    @Json(name = "message")
    val message : String,
    @Json(name = "error")
    val error : String,
    @Json(name = "statusCode")
    val statusCode : Int,
) : Parcelable