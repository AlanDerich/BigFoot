package com.derich.bigfoot.ui.screens.loans

data class Loan(
    var username: String = "",
    var amountLoaned: Int = 0,
    var dateLoaned: String = "null",
    var status: Boolean = false,
    var transactionCharges: Int = 0,
    var dateRepaid: String? = null,
    var amountRepaid: Int? = 0
)