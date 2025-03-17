package com.mercierlucas.feedarticlesjetpack.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatApiDate(dateSt: String): String {
    val dateInputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val dateOutputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    val objectDatefromdateStr = dateInputFormat.parse(dateSt)
    return dateOutputFormat.format(objectDatefromdateStr) ?: "not a date"
}

fun convertMillisToDate(millis: Long): String {
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
    return formatter.format(Date(millis))
}