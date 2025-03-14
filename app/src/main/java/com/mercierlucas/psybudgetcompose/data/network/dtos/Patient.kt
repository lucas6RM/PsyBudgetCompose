package com.mercierlucas.psybudgetcompose.data.network.dtos

import com.squareup.moshi.Json


data class PatientDto(
    @Json(name = "address")
    val address: String?,
    @Json(name = "age")
    val age: Int?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "id")
    val id: Long,
    @Json(name = "income")
    val income: String?,
    @Json(name = "isActive")
    val isActive: Boolean,
    @Json(name = "isRequiringInvoice")
    val isRequiringInvoice: Boolean,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "numSS")
    val numSS: Int?,
    @Json(name = "phoneNumber")
    val phoneNumber: String?,
    @Json(name = "postalCode")
    val postalCode: Int?,
)

data class CreatePatientDto(
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "email")
    val email: String?,
    @Json(name = "numSS")
    val numSS: Int?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "postalCode")
    val postalCode: Int?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "age")
    val age: Int?,
    @Json(name = "income")
    val income: String,
    @Json(name = "isActive")
    val isActive: Boolean,
    @Json(name = "isRequiringInvoice")
    val isRequiringInvoice: Boolean,
)

data class UpdatePatientDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "email")
    val email: String?,
    @Json(name = "numSS")
    val numSS: Int?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "postalCode")
    val postalCode: Int?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "age")
    val age: Int?,
    @Json(name = "income")
    val income: String,
    @Json(name = "isActive")
    val isActive: Boolean,
    @Json(name = "isRequiringInvoice")
    val isRequiringInvoice: Boolean,
)