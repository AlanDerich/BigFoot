package com.derich.bigfoot.ui.screens.transactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.derich.bigfoot.ui.bottomnavigation.BottomNavItem
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {
    private val repository: TransactionsRepository = TransactionsRepository()
    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Transactions>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )
    init {
        getAllTransactions()
    }

    private fun getAllTransactions() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getAllTransactionsFromFirestone()
            loading.value = false
        }
    }
    fun launchAddTransactionScreen(navController: NavController) {
        navController.navigate(BottomNavItem.AddTransaction.screen_route)
    }
    fun addTransaction(transactionDetails: Transactions, contributionDate: String) {

    }
}