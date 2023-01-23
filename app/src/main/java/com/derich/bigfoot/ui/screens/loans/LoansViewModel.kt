package com.derich.bigfoot.ui.screens.loans

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.launch

class LoansViewModel : ViewModel() {
    private val repository: LoansRepository = LoansRepository()
    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Loan>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )
    init {
        getAllLoans()
    }

    private fun getAllLoans() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getAllLoansFromFirestone()
            loading.value = false
        }
    }
}