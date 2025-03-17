package com.mercierlucas.psybudgetcompose.data.custom

data class SessionByDayLite(
    val sessionId : Long,
    val sessionDate : String,
    val patientId : Long,
    val patientFirstName : String,
    val patientLastNAme : String
)


data class SessionsByDayStats(
    val numberOfCompletedSessions : Int,
    val totalAmountPaid: Int,
    val totalAmountToBeValidated: Int
)
