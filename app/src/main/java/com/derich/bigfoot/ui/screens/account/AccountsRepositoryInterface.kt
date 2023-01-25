package com.derich.bigfoot.ui.screens.account

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface AccountsRepositoryInterface {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<ResponseFromDb<Uri>>

    suspend fun addImageToFirestore(downloadUrl: Uri): Flow<ResponseFromDb<Boolean>>

    suspend fun getImageFromFirestore(): Flow<ResponseFromDb<String>>
}