package com.derich.bigfoot.ui.screens.account

import android.widget.ProgressBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.derich.bigfoot.ui.common.CircularProgressBar
import com.derich.bigfoot.ui.screens.home.MemberDetails
import com.derich.bigfoot.ui.screens.login.AuthViewModel
import com.derich.bigfoot.ui.theme.BigFootTheme
import kotlin.coroutines.coroutineContext

@Composable
fun AccountsComposable(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    accountsViewModel: AccountsViewModel,
    navController: NavController,
    memberInfo: MemberDetails
) {
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher =  rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            accountsViewModel.addImageToStorage(imageUri)
        }
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        BadgedBox(badge = {
            Badge {  IconButton(content = { Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = modifier
                    .size(16.dp)
                    .clip(MaterialTheme.shapes.medium))
            },
            onClick = { onClick() }) } }) {
            Image(painter = painterResource(id = com.derich.bigfoot.R.drawable.bigfut1),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(140.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

        }

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
@Composable
fun onClick() {
    ProfileImageContent(
        openGallery = {
            galleryLauncher.launch(ALL_IMAGES)
        }
    )
    when(val addImageToStorageResponse = accountsViewModel.addImageToStorageState.value) {
        is ResponseFromDb.Loading -> CircularProgressBar(isDisplayed = true)
        is ResponseFromDb.Success -> {
            val isImageAddedToStorage = addImageToStorageResponse.data
            isImageAddedToStorage?.let { downloadUrl ->
                LaunchedEffect(isImageAddedToStorage) {
                    accountsViewModel.addImageToDatabase(downloadUrl)
                }
            }
        }
        is ResponseFromDb.Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }

    }
@Preview
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    BigFootTheme {
//        AccountsComposable(userProfile = userProfile.user)
    }
}