package com.derich.bigfoot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.derich.bigfoot.ui.composables.TopBar
import com.derich.bigfoot.ui.theme.BigFootTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigFootTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WelcomePage(name = "Alan")
                }
            }
        }
    }
}

@Composable
fun WelcomePage(modifier: Modifier = Modifier, name: String) {
    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
            contentPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Hello $name! Welcome to BigFoot.")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BigFootTheme {
        WelcomePage(name = "Alan G")
    }
}