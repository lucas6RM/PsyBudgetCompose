package com.mercierlucas.psybudgetcompose.data.network.dtos

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class ErrorResponseDto(
    @Json(name = "message")
    val message : String,
    @Json(name = "error")
    val error : String,
    @Json(name = "statusCode")
    val statusCode : Int,
)


data class ResponseUpdatePatientDto(
    @Json(name = "updatedPatient")
    val updatedPatient: PatientDto
)


data class ResponseDeletePatientDto(
    @Json(name = "deletedPatient")
    val deletedPatient: DeletedPatient
)

data class DeletedPatient(
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
)

// -------------------Response session Created ----------------------------- //

data class ResponseSessionCreated(
    @Json(name = "message")
    val message: String,
    @Json(name = "patient")
    val patient: PatientNames,
    @Json(name = "session")
    val session: String
)

data class PatientNames(
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
)




// -------------- Response Sessions By Day ----------------------

data class ResponseSessionByDayDto(
    @Json(name = "numberOfCompletedSessions")
    val numberOfCompletedSessions: Int?,
    @Json(name = "sessions")
    val sessions: List<SessionDto>,
    @Json(name = "totalAmountPaid")
    val totalAmountPaid: Int?,
    @Json(name = "totalAmountToBeValidated")
    val totalAmountToBeValidated: Int?
)


data class PatientLiteDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String
)

data class SessionDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "isSessionCancelled")
    val isSessionCancelled: Boolean,
    @Json(name = "isStateAgreementUsed")
    val isStateAgreementUsed: Boolean,
    @Json(name = "patient")
    val patient: PatientLiteDto,
    @Json(name = "sessionDate")
    val sessionDate: String,
    @Json(name = "transaction")
    val transaction: TransactionDto
)

data class TransactionDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "amountDue")
    val amountDue: Int,
    @Json(name = "isPaymentValidated")
    val isPaymentValidated: Boolean,
    @Json(name = "paymentMethod")
    val paymentMethod: String
)


//------------------- Response Delete Session By ID ---------------- //

data class ResponseDeleteSessionDto(
    @Json(name = "sessionDeleted")
    val sessionDeleted: SessionDeleted
)

data class SessionDeleted(
    @Json(name = "sessionDate")
    val sessionDate: String,
    @Json(name = "patientFirstName")
    val patientFirstName: String,
    @Json(name = "patientLastName")
    val patientLastName: String,
)

// ---------- Response Sessions Due This Year ------------ //

data class ResponseSessionsDueThisYear(
    @Json(name = "sessionsDueThisYear")
    val sessionsDueThisYear: List<SessionDueThisYear>
)

data class SessionDueThisYear(
    @Json(name = "id")
    val id: Long,
    @Json(name = "isStateAgreementUsed")
    val isStateAgreementUsed: Boolean,
    @Json(name = "patient")
    val patient: PatientLiteDto,
    @Json(name = "sessionDate")
    val sessionDate: String,
    @Json(name = "transaction")
    val transaction: TransactionLite
)

data class TransactionLite(
    @Json(name = "id")
    val id: Long,
    @Json(name = "amountDue")
    val amountDue: Int,
    @Json(name = "isPaymentValidated")
    val isPaymentValidated: Boolean,
    @Json(name = "paymentMethod")
    val paymentMethod: String
)

//------- Response Update Transaction Dto --------

data class ResponseUpdateTransactionDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "amountDue")
    val amountDue: Int,
    @Json(name = "isPaymentValidated")
    val isPaymentValidated: Boolean,
    @Json(name = "paymentMethod")
    val paymentMethod: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "updatedAt")
    val updatedAt: String
)

//----- Response Get Sessions This Month -----

data class ResponseSessionsThisMonth(
    @Json(name = "sessionsThisMonth")
    val sessionsThisMonth: List<SessionsThisMonth>
)

data class SessionsThisMonth(
    @Json(name = "id")
    val id: Int,
    @Json(name = "isStateAgreementUsed")
    val isStateAgreementUsed: Boolean,
    @Json(name = "sessionDate")
    val sessionDate: String,
    @Json(name = "transaction")
    val transaction: TransactionLite
)

