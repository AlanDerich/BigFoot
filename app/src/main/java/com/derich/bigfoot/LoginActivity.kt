package com.derich.bigfoot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.derich.bigfoot.ui.theme.BigFootTheme
import androidx.compose.material.Surface
import com.derich.bigfoot.ui.composables.TopBar

class LoginActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginMember()
        }
    }

    @Composable
    private fun LoginMember() {
        BigFootTheme {

        }
    }
}