package com.mercierlucas.psybudgetcompose.data.network.dtos

import android.os.Parcelable
import com.mercierlucas.psybudgetcompose.data.model.UserDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize



@Parcelize
data class ErrorResponseDto(
    @Json(name = "message")
    val message : String,
    @Json(name = "error")
    val error : String,
    @Json(name = "statusCode")
    val statusCode : Int,
) : Parcelable