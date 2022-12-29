package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(8.dp)) {
            Text(text = "BigFut", fontSize = 20.sp)
    }
}