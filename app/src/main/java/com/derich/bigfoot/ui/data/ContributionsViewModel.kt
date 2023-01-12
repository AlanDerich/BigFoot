package com.derich.bigfoot.ui.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributionsViewModel @Inject constructor(
    private val repository: ContributionsHistoryRepository
): ViewModel() {
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