package com.mercierlucas.psybudgetcompose.data.local

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPrefs @Inject constructor(val prefs: SharedPreferences) {

    companion object{
        const val PREF_FILENAME = "com.mercierlucas.psybudgetcompose.prefs"
        const val TOKEN = "token"
        const val USER_ID = "userId"
        const val USER_FIRST_NAME = "userFirstName"
        const val USER_LAST_NAME = "userLastName"
    }


    var userId: Long
        get() = prefs.getLong(USER_ID, 0L)
        set(value) = prefs.edit().putLong(USER_ID, value).apply()

    var token: String?
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var userFirstName : String?
        get() = prefs.getString(USER_FIRST_NAME, null)
        set(value) = prefs.edit().putString(USER_FIRST_NAME,value).apply()

    var userLastName : String?
        get() = prefs.getString(USER_LAST_NAME, null)
        set(value) = prefs.edit().putString(USER_LAST_NAME,value).apply()

}