package com.derich.bigfoot.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ContributionsViewModel : ViewModel() {
    private val contributionsRepository: ContributionsHistoryRepository = ContributionsHistoryRepository()
    var loadingContributions = mutableStateOf(false)
    var loadingMemberDetails = mutableStateOf(false)
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
    fun calculateContributionsDifference(totalAmount: Int) : Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val currentDate = sdf.format(Date())
        val currDate = sdf.parse(currentDate)
        val startDate = sdf.parse("31/12/2019")
        val diff: Long = currDate!!.time - startDate!!.time
        val daysRemaining: Long = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        return ((daysRemaining.toInt() * 20) - totalAmount)
    }
}