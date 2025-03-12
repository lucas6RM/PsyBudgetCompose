package com.mercierlucas.psybudgetcompose.data.network.dtos

import android.os.Parcelable
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


data class ResponseUpdatePatientDto(
    @Json(name = "updatedPatient")
    val updatedPatient: PatientDto
)


data class RespondeDeletePatientDto(
    @Json(name = "deletedPatient")
    val deletedPatient: DeletedPatient
)

data class DeletedPatient(
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
)