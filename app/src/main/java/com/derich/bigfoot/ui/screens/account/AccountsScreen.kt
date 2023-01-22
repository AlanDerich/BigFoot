package com.derich.bigfoot.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.screens.login.AuthViewModel
import com.derich.bigfoot.ui.theme.BigFootTheme

@Composable
fun AccountsComposable(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.bigfut1),
            contentDescription = "App Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(72.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Text(text = "Alan Derich",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(8.dp))
        Text(text = authViewModel.authState.currentUser!!.phoneNumber!!,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(8.dp))
        Button(onClick = {
            authViewModel.logOut(navController)

        },
                modifier = Modifier.padding(8.dp)) {
            Text(text = "Log Out",
                style = MaterialTheme.typography.button)
        }
    }
}

@Preview
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    BigFootTheme {
//        AccountsComposable(userProfile = userProfile.user)
    }
}