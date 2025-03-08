package com.mercierlucas.psybudgetcompose.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val myPrefs: MyPrefs
): ViewModel(){



    private val _isUserLoggedSharedFlow = MutableSharedFlow<Boolean>()
    val isUserLoggedSharedFlow = _isUserLoggedSharedFlow.asSharedFlow()


    init {
        // ------ Attention !!!! à supprimer
        myPrefs.token = null

        viewModelScope.launch {
            delay(2000)
            _isUserLoggedSharedFlow.emit(myPrefs.token != null)
        }
    }
}