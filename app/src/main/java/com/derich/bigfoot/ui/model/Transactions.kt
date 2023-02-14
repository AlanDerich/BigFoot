package com.derich.bigfoot.ui.model

data class Transactions (
    var transactionDate:String = "",
    var depositFor:String = "",
    var depositBy:String = "",
    var transactionAmount:Int = 0,
    var transactionConfirmation:String = ""
)