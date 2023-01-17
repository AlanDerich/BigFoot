package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransactionsComposable(modifier: Modifier = Modifier) {
    Text(text = "This is transactions Screen", modifier = modifier.fillMaxSize())
}