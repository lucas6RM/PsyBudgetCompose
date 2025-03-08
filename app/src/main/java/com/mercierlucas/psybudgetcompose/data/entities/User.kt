package com.mercierlucas.psybudgetcompose.data.entities

import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "address")
    val address: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "id")
    val id: Long,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String?,
    @Json(name = "postalCode")
    val postalCode: String?
)

