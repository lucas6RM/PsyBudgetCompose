package com.mercierlucas.psybudgetcompose.data.network.api

object ApiRoutes {

    const val BASE_URL ="http://10.0.2.2:3000"

    const val LOGIN_USER = "auth/login"
    const val REGISTER_USER = "auth/register"
    const val GET_ALL_PATIENTS = "patients"
    const val CREATE_PATIENT = "patients/new"
    const val GET_PATIENT = "patients/{id}"
    const val UPDATE_PATIENT = "patients/update/{id}"
    const val DELETE_PATIENT = "patients/delete/{id}"
    const val GET_REMAINING_AGREEMENTS = "sessions/agreements"
    const val CREATE_SESSION = "sessions/new"
    const val GET_SESSIONS_BY_DAY = "sessions/today"
    const val GET_SESSION_BY_ID = "sessions/{id}"
    const val DELETE_SESSION_BY_ID = "sessions/delete/{id}"
    const val GET_SESSIONS_DUE_THIS_YEAR = "sessions/year"

}