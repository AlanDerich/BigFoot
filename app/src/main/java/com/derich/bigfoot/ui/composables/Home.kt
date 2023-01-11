package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.R

@Composable
fun HomeComposable(modifier: Modifier = Modifier.fillMaxSize()) {
    Row(modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Start) {
        Image(painter = painterResource(id = R.drawable.bigfut1),
            contentDescription = "User profile image",
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .size(64.dp))
        UsersColumn()

    }
}

@Composable
fun UsersColumn(modifier: Modifier = Modifier.padding(8.dp)) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        Text(text = "Alan Gitonga Wanjiru")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "KSH12000")
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "01/01/2023")
    }
}