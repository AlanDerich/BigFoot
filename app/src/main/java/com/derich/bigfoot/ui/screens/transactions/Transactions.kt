package com.derich.bigfoot.ui.screens.transactions

data class Transactions (
    var transactionDate:String? = null,
    var depositFor:String? = null,
    var depositBy:String? = null,
    var transactionAmount:Int? = null,
    var transactionConfirmation:String? = null
)