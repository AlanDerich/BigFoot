package com.derich.bigfoot.ui.common

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}