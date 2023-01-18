package com.derich.bigfoot.ui.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject
constructor(private val accountService: AuthService)
    : ViewModel() {

    val signUpState: MutableStateFlow<Response> = accountService.signUpState

    private val _number: MutableStateFlow<String> = MutableStateFlow("")
    val number: StateFlow<String> get() = _number

    private val _code: MutableStateFlow<String> = MutableStateFlow("")
    val code: StateFlow<String> get() = _code

//    var isSignedIn: Boolean = false
    private var _signInStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
//        set(value) = isSignedIn
    val signInStatus: StateFlow<Boolean> get() = _signInStatus

    //check if user is signed in
    val user = Firebase.auth.currentUser

    fun authenticatePhone(phone: String) {
        accountService.authenticate(phone)
    }
    fun logOut() {
        FirebaseAuth.getInstance()
            .signOut()
        _signInStatus.value = false
    }
    fun logIn() {
        _signInStatus.value = true
    }
    fun resetAuthState() {
        signUpState.value = Response.NotInitialized
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