package com.derich.bigfoot.ui.data

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContributionsHistoryRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    suspend fun getContributionsFromFirestone(): DataOrException<List<Contributions>, Exception> {
        val dataOrException = DataOrException<List<Contributions>, Exception>()
        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                document.toObject(Contributions::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}