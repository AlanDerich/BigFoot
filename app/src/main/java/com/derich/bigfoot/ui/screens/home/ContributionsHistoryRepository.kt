package com.derich.bigfoot.ui.screens.home

import com.derich.bigfoot.ui.common.firestorequeries.FirestoreQueries
import com.derich.bigfoot.ui.data.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class ContributionsHistoryRepository {
    private val firestoreQueries: FirestoreQueries = FirestoreQueries()
    private val getAllContributions : Query = firestoreQueries.queryAllContributions()
    suspend fun getContributionsFromFirestone(): DataOrException<List<Contributions>, Exception> {
        val dataOrException = DataOrException<List<Contributions>, Exception>()
        try {
            dataOrException.data = getAllContributions.get().await().map { document ->
                document.toObject(Contributions::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}