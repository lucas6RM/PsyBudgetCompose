package com.mercierlucas.psybudgetcompose.ui.transactions.incomes

import androidx.lifecycle.ViewModel
import com.mercierlucas.psybudgetcompose.data.local.MyPrefs
import com.mercierlucas.psybudgetcompose.data.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomesByMonthViewModel @Inject constructor(
    private val myPrefs: MyPrefs,
    private val apiService: ApiService
) : ViewModel() {


}