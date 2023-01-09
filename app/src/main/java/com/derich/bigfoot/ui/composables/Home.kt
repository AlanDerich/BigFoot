package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeComposable(modifier: Modifier = Modifier.fillMaxSize()) {
    Text(text = "Welcome Home Alan", modifier = modifier.padding(8.dp))

}