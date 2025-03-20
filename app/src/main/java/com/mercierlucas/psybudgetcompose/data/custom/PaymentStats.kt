package com.mercierlucas.psybudgetcompose.data.custom

data class PaymentStats(
    val numberAgreementUsed    : Int,
    val creditCardSumPaid      : Int,
    val creditCardSumDue       : Int,
    val bankCheckSumPaid        : Int,
    val bankCheckSumDue        : Int,
    val bankTransferSumPaid    : Int,
    val bankTransferSumDue     : Int,
    val cashSumPaid            : Int,
    val cashSumDue             : Int,
    val thirdPartyPayerSumPaid : Int,
    val thirdPartyPayerSumDue  : Int,
    val totalPaid              : Int,
    val totalDue               : Int,
)

