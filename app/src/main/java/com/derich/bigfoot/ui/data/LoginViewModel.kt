package com.derich.bigfoot.ui.data

import androidx.compose.runtime.mutableStateOf

class LoginViewModel {
    var uiState = mutableStateOf(LoginUiState())
            private set

}