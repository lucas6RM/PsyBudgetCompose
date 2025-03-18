package com.mercierlucas.psybudgetcompose.data.network.dtos

import com.squareup.moshi.Json

data class UpdateTransactionDto(
    @Json(name = "amountDue")
    val amountDue: Int,
    @Json(name = "isPaymentValidated")
    val isPaymentValidated: Boolean,
    @Json(name = "patientId")
    val patientId: Long,
    @Json(name = "paymentMethod")
    val paymentMethod: String
)