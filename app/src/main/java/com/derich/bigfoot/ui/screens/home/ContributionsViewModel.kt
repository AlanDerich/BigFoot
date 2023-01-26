package com.derich.bigfoot.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.launch


class ContributionsViewModel : ViewModel() {
    private val contributionsRepository: ContributionsHistoryRepository = ContributionsHistoryRepository()
    var loadingContributions = mutableStateOf(false)
    private var loadingMemberDetails = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Contributions>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )
    val memberData: MutableState<DataOrException<List<MemberDetails>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        getContributions()
        getMemberDetails()
    }

    private fun getMemberDetails() {
        viewModelScope.launch {
            loadingMemberDetails.value = true
            memberData.value = contributionsRepository.getMemberDetailsFromFirestore()
            loadingMemberDetails.value = false
        }
    }

    private fun getContributions() {
        viewModelScope.launch {
            loadingContributions.value = true
            data.value = contributionsRepository.getContributionsFromFirestone()
            loadingContributions.value = false
        }
    }
}