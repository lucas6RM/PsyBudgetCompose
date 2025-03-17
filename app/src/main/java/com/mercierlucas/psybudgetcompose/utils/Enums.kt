package com.mercierlucas.psybudgetcompose.utils

enum class Income(val str : String){
    LOW("Low"),
    MID("Mid"),
    HIGH("High")
}

enum class PaymentMethod(val str : String){
    CB("CB"),
    CHEQUE("cheque"),
    ESPECES("especes"),
    VIREMENT("virement"),
    TIERS_PAYANT("tiers payant")
}


enum class DestinationsFromMainMenuTo{
    PATIENTS, SESSIONS, CREATE_SESSION, TRANSACTIONS, PAID_INVOICES, STATISTICS,SETTINGS
}

enum class DestinationsFromSessionsMenuTo{
    CREATE_SESSION, DAILY_SESSIONS, SESSIONS_BY_PERIOD, SESSIONS_BY_PATIENTS
}

enum class DestinationsFromTransactionsMenuTo{
    INCOMES_BY_MONTH, VALIDATE_PAYMENTS, TRANSACTIONS_BY_PATIENTS
}