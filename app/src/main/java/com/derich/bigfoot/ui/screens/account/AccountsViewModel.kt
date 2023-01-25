package com.derich.bigfoot.ui.screens.account

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derich.bigfoot.ui.data.DataOrException
import com.derich.bigfoot.ui.screens.account.ResponseFromDb.Loading
import com.derich.bigfoot.ui.screens.account.ResponseFromDb.Success
import com.derich.bigfoot.ui.screens.home.Contributions
import kotlinx.coroutines.launch

class AccountsViewModel(userName: String): ViewModel() {
    private val repo: AccountRepository = AccountRepository(userName)
    var loading = mutableStateOf(false)

    var addImageToStorageResponse by mutableStateOf<ResponseFromDb<Uri>>(Success(null))
        private set
    var addImageToDatabaseResponse by mutableStateOf<ResponseFromDb<Boolean>>(Success(null))
        private set
    var getImageFromDatabaseResponse by mutableStateOf<ResponseFromDb<String>>(Success(null))
        private set

    val data: MutableState<DataOrException<List<Contributions>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )
    val memberContribution: List<Contributions>? = data.value.data
    var contribution: Contributions?=  memberContribution!![0]
        init {
        getMemberContributions()
    }

    private fun getMemberContributions() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getContributionsFromFirestone()
            loading.value = false
        }
    }

    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        addImageToStorageResponse = Loading
        addImageToStorageResponse = repo.addImageToFirebaseStorage(imageUri)
    }

    fun addImageToDatabase(downloadUrl: Uri) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)
    }

    fun getImageFromDatabase() = viewModelScope.launch {
        getImageFromDatabaseResponse = Loading
        getImageFromDatabaseResponse = repo.getImageUrlFromFirestore()
    }
}