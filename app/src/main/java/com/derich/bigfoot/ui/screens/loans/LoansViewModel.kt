package com.derich.bigfoot.ui.screens.loans

import androidx.compose.runtime.mutableStateListOf
import com.derich.bigfoot.ui.common.BigFootViewModel
import com.derich.bigfoot.ui.common.LogService
import com.derich.bigfoot.ui.common.firestorequeries.StorageService
import com.derich.bigfoot.ui.model.Loan
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoansViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : BigFootViewModel(logService) {
    var loans: List<Loan> = mutableStateListOf()
    fun initialize() {
        launchCatching {
                loans = storageService.getLoans()
        }
    }
}