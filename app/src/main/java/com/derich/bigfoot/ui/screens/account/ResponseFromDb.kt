package com.derich.bigfoot.ui.screens.account

sealed class ResponseFromDb<out T> {
    object Loading: ResponseFromDb<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ResponseFromDb<T>()

    data class Failure(
        val e: Exception
    ): ResponseFromDb<Nothing>()
}