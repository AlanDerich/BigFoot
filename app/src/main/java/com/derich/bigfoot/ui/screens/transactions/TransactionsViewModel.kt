package com.derich.bigfoot.ui.screens.transactions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.derich.bigfoot.ui.bottomnavigation.BottomNavItem
import com.derich.bigfoot.ui.common.BigFootViewModel
import com.derich.bigfoot.ui.common.LogService
import com.derich.bigfoot.ui.common.firestorequeries.StorageService
import com.derich.bigfoot.ui.model.Transactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : BigFootViewModel(logService) {

    var transactions: List<Transactions> = mutableStateListOf()
    private val repository: TransactionsRepository = TransactionsRepository()
    var uploadStatus = mutableStateOf(false)
    var transactionUploadStatus = false
    var contUploadStatus = false

    fun initialize() {
        launchCatching {
            transactions = storageService.getAllTransactions()
        }
    }
//    init {
//        getAllTransactions()
//    }

//    private fun getAllTransactions() {
//        viewModelScope.launch {
//            loading.value = true
//            data.value = repository.getAllTransactionsFromFirestone()
//            loading.value = false
//        }
//    }
    fun launchAddTransactionScreen(navController: NavController) {
        navController.navigate(BottomNavItem.AddTransaction.screen_route)
    }
    fun launchTransactionScreen(navController: NavController) {
        navController.navigate(BottomNavItem.Transactions.screen_route)
    }
    fun addTransaction(transactionDetails: Transactions,
                       previousAmount: Int,
                       memberPhone: String) {
        //check if upload job is complete or not
        val waitingForResponse = viewModelScope.launch {
            transactionUploadStatus = repository.uploadTransactionToDb(transactionDetails)
            contUploadStatus = repository.updateMemberContributions(
                memberPhoneNumber = memberPhone,
                memberFullNames = transactionDetails.depositFor,
                resultingDate = calculateResultingDate(previousAmount + (transactionDetails.transactionAmount)),
                newUserAmount = (previousAmount + (transactionDetails.transactionAmount)).toString())
        }.isActive
        uploadStatus.value = waitingForResponse
    }
    private fun calculateResultingDate(newUserAmount: Int): String {
        val totalDays: Int = (newUserAmount / 20)
        //add number of days to the start date
        var sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val c: Calendar = Calendar.getInstance()
        c.time = sdf.parse("31/12/2019")!!
        c.add(Calendar.DATE, totalDays)
        sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(Date(c.timeInMillis))
    }
}