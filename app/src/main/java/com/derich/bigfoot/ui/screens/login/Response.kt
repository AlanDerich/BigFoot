package com.derich.bigfoot.ui.screens.login

sealed class Response {
    object NotInitialized : Response()
    class Loading(val message: String?) : Response()
    class Success(val message: String?) : Response()
    class Error(val exception: Throwable?) : Response()
}