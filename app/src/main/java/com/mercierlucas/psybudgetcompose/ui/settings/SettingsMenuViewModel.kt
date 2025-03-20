package com.mercierlucas.psybudgetcompose.ui.settings

import androidx.lifecycle.ViewModel
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsMenuViewModel@Inject constructor(
    private val myPrefs: MyPrefs
):ViewModel(){

    fun logout() {
        myPrefs.token = null
        myPrefs.userFirstName = null
        myPrefs.userLastName = null
        myPrefs.userId = 0L
    }

}