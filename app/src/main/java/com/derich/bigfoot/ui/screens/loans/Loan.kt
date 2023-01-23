package com.derich.bigfoot.ui.screens.loans

data class Loan(
    var username: String = "",
    var amountLoaned: String = "",
    var dateLoaned: String = "null",
    var status: Boolean = false,
    var transactionCharges: String = "",
    var dateRepaid: String? = null,
    var amountRepaid: String? = null
)