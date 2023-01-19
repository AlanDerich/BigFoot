package com.derich.bigfoot.ui.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject
constructor(private val accountService: AuthService,
            private val firebaseAuth: FirebaseAuth)
    : ViewModel() {

    val signUpState: MutableStateFlow<Response> = accountService.signUpState

    private val _number: MutableStateFlow<String> = MutableStateFlow("")
    val number: StateFlow<String> get() = _number
    val authState: FirebaseAuth = firebaseAuth
    private val _code: MutableStateFlow<String> = MutableStateFlow("")
    val code: StateFlow<String> get() = _code
    fun authenticatePhone(phone: String) {
        accountService.authenticate(phone)
    }
    fun logOut() {
        firebaseAuth.signOut()
        resetAuthState()

    }
    fun resetAuthState() {
        signUpState.value = Response.NotInitialized
        _code.value = ""
    }

    fun onNumberChange(number: String) {
        _number.value = number
    }

    fun onCodeChange(code: String) {
        _code.value = code.take(6)
    }

    fun verifyOtp(code: String) {
        viewModelScope.launch {
            accountService.onVerifyOtp(code)
        }
    }


}