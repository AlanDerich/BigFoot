package com.derich.bigfoot.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.launch


class ContributionsViewModel : ViewModel() {
    private val repository: ContributionsHistoryRepository = ContributionsHistoryRepository()
    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Contributions>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        getContributions()
    }

    private fun getContributions() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getContributionsFromFirestone()
            loading.value = false
        }
    }
}