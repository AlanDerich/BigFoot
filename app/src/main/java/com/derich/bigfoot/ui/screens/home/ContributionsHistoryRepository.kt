package com.derich.bigfoot.ui.screens.home

import com.derich.bigfoot.ui.common.firestorequeries.FirestoreQueries
import com.derich.bigfoot.ui.data.DataOrException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class ContributionsHistoryRepository {
    private val firestoreQueries: FirestoreQueries = FirestoreQueries()
    private val getAllContributions : Query = firestoreQueries.queryAllContributions()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //get memberdetails from db
    private val getMemberDetails : Query = firestoreQueries.queryAllMemberDetails()

    suspend fun getMemberDetailsFromFirestore(): DataOrException<List<MemberDetails>, Exception> {
        val dataOrException = DataOrException<List<MemberDetails>, Exception>()
        try {
            dataOrException.data = getMemberDetails.get().await().map { document ->
                document.toObject(MemberDetails::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}