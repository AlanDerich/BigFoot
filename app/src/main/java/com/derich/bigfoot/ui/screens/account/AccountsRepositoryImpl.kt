package com.derich.bigfoot.ui.screens.account

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountsRepositoryInterfaceImpl @Inject constructor(
    private val imagesStorageRef: StorageReference,
    private val imagesCollRef: CollectionReference
) : AccountsRepositoryInterface {
    val db = FirebaseFirestore.getInstance()
    override suspend fun addImageToFirebaseStorage(imageUri: Uri) = flow {
        try {
            emit(ResponseFromDb.Loading)
            val downloadUrl = imagesStorageRef.child("profilepics/${fullNames}")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            emit(ResponseFromDb.Success(downloadUrl))
        } catch (e: Exception) {
            emit(ResponseFromDb.Failure(e))
        }
    }

    override suspend fun addImageToFirestore(downloadUrl: Uri) = flow {
        try {
            emit(ResponseFromDb.Loading)
            db.collection("TotalContributions")
                .document("allMembersTotals")
                .collection("Totals")
                .document(userName)
                .set(totalTransactionInfo).await()
            emit(ResponseFromDb.Success(true))
        } catch (e: Exception) {
            emit(ResponseFromDb.Failure(e))
        }
    }

    override suspend fun getImageFromFirestore() = flow {
        try {
            emit(ResponseFromDb.Loading)
            val url = db.collection("TotalContributions")
                .document("allMembersTotals")
                .collection("Totals")
                .document(userName).get().await()
            emit(ResponseFromDb.Success(url))
        } catch (e: Exception) {
            emit(ResponseFromDb.Failure(e))
        }
    }
}