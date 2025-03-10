package com.mercierlucas.psybudgetcompose.data.model

import com.squareup.moshi.Json

data class PatientLite(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean,
)

