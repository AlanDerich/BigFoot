package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.R

@Composable
fun BigFutAppBar(modifier: Modifier = Modifier.fillMaxWidth()) {
    Row(modifier = modifier.background(color = Color.Blue), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.bigfut1),
            contentDescription = "App Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .border(width = 2.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.small))
        Text(text = "BigFut",
            style = MaterialTheme.typography.h4,
            color = Color.White)
    }
}