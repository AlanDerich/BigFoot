package com.derich.bigfoot.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.model.Response

@Composable
fun PhoneLoginUI(
    navigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
    restartLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Sign up state
    val uiState by viewModel.signUpState
        .collectAsState(initial = Response.NotInitialized)
    // SMS code
    val code by viewModel.code.collectAsState(initial = "")

    // Phone number
    val phone by viewModel.number.collectAsState(initial = "")

    val focusManager = LocalFocusManager.current
//    if (viewModel.authState.currentUser != null){
//        LaunchedEffect(key1 = "navigateHome") {
//            navigateToHome()
//        }
//        Log.d("Code", "Previously logged in")
//    }
//    else{
    when (uiState) {
        // Nothing happening yet
        is Response.NotInitialized -> {
            EnterPhoneNumberUI(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.authenticatePhone(phone)
                },
                phone = phone,
                onPhoneChange = viewModel::onNumberChange,
                onDone = {
                    focusManager.clearFocus()
                    viewModel.authenticatePhone(phone)
                }
            )
        }

        // State loading
        is Response.Loading -> {
            val text = (uiState as Response.Loading).message
            if (text == context.getString(R.string.code_sent)) {

                // If the code is sent, display the screen for code
                EnterCodeUI(
                    code = code,
                    onCodeChange = viewModel::onCodeChange,
                    phone = phone,
                    onGo = {
                        Log.d("Code Sent", "The code is $code")
                        focusManager.clearFocus()
                        viewModel.verifyOtp(code)
                    },
                onNext = {
                    Log.d("Code Sent", "The code is $code")
                    focusManager.clearFocus()
                    viewModel.verifyOtp(code)
                })

            } else {

                // If the loading state is different form the code sent state,
                // show a progress indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    text?.let {
                        Text(
                            it, modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                }


            }

        }

        // If it is the error state, show the error UI
        is Response.Error -> {
            val throwable = (uiState as Response.Error).exception!!
            ErrorUi(exc = throwable, onRestart = restartLogin)
        }

        // You can navigate when the auth process is successful
        is Response.Success -> {
            Log.d("Code", "The Sign in was successful")
            viewModel.resetAuthState()
            LaunchedEffect(key1 = "navigateToHome") {
                navigateToHome()
            }
        }
    }
}
//        }
