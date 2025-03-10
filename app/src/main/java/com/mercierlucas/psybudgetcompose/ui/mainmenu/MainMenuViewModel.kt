package com.mercierlucas.psybudgetcompose.ui.mainmenu

import androidx.lifecycle.ViewModel
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val myPrefs: MyPrefs
): ViewModel(){

    private val _userFirstNameStateFlow = MutableStateFlow(myPrefs.userFirstName)
    val userFirstNameStateFlow = _userFirstNameStateFlow.asStateFlow()



}