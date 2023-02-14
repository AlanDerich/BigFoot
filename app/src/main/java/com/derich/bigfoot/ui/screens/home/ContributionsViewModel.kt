package com.derich.bigfoot.ui.screens.home

import androidx.compose.runtime.mutableStateListOf
import com.derich.bigfoot.ui.common.BigFootViewModel
import com.derich.bigfoot.ui.common.LogService
import com.derich.bigfoot.ui.common.firestorequeries.StorageService
import com.derich.bigfoot.ui.model.MemberDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ContributionsViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : BigFootViewModel(logService) {
    var members: List<MemberDetails> = mutableStateListOf()
    private val contributionsRepository: ContributionsHistoryRepository = ContributionsHistoryRepository()
//    var loadingContributions = mutableStateOf(false)
//    var loadingMemberDetails = mutableStateOf(false)
    fun initialize() {
    launchCatching {
        members = storageService.getMembers()
    }
}


//    private fun getMemberDetails() {
//        viewModelScope.launch {
//            loadingMemberDetails.value = true
//            memberData.value = contributionsRepository.getMemberDetailsFromFirestore()
//            loadingMemberDetails.value = false
//        }
//    }

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