package com.mercierlucas.psybudgetcompose.data.network.dtos

import com.squareup.moshi.Json

class CreateSessionDto(
    @Json(name = "sessionDate")
    val sessionDate: String,
    @Json(name = "patientId")
    val patientId: Long,
    @Json(name = "paymentMethod")
    val paymentMethod: String,
    @Json(name = "amountDue")
    val amountDue: Int,
    @Json(name = "isPaymentValidated")
    val isPaymentValidated: Boolean,
    @Json(name = "isSessionCancelled")
    val isSessionCancelled: Boolean,
    @Json(name = "isStateAgreementUsed")
    val isStateAgreementUsed: Boolean,
)

