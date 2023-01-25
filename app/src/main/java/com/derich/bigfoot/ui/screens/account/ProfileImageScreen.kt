package com.derich.bigfoot.ui.screens.account

import android.widget.ProgressBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.derich.bigfoot.ui.screens.account.ResponseFromDb.*
import kotlinx.coroutines.launch

@Composable
fun ProfileImageScreen(
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher =  rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            viewModel.addImageToStorage(imageUri)
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                ProfileImageContent(
                    openGallery = {
                        galleryLauncher.launch(ALL_IMAGES)
                    }
                )
            }
        },
        scaffoldState = scaffoldState
    )

    when(val addImageToStorageResponse = viewModel.addImageToStorageState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            val isImageAddedToStorage = addImageToStorageResponse.data
            isImageAddedToStorage?.let { downloadUrl ->
                LaunchedEffect(isImageAddedToStorage) {
                    viewModel.addImageToDatabase(downloadUrl)
                }
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }
    }

    fun showSnackBar() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = IMAGE_SUCCESSFULLY_ADDED_MESSAGE,
            actionLabel = DISPLAY_IT_MESSAGE
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.getImageFromDatabase()
        }
    }

    when(val addImageToDatabaseResponse = viewModel.addImageToDatabaseState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            val isImageAddedToDatabase = addImageToDatabaseResponse.data
            isImageAddedToDatabase?.let {
                if (isImageAddedToDatabase) {
                    LaunchedEffect(isImageAddedToDatabase) {
                        showSnackBar()
                    }
                }
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(addImageToDatabaseResponse.e)
        }
    }
}