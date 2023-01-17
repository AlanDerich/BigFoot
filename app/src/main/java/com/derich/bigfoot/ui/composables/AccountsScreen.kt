package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountsComposable(modifier: Modifier = Modifier) {
    Text(text = "This is Accounts Screen", modifier = modifier.fillMaxSize())
}