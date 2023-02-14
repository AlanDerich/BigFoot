package com.derich.bigfoot.ui.screens.loans

import com.derich.bigfoot.ui.common.firestorequeries.FirestoreQueries
import com.derich.bigfoot.ui.data.DataOrException
import com.derich.bigfoot.ui.model.Loan
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class LoansRepository {
    private val firestoreQueries: FirestoreQueries = FirestoreQueries()
    private val getAllLoans : Query = firestoreQueries.queryAllLoans()
    suspend fun getAllLoansFromFirestone(): DataOrException<List<Loan>, Exception> {
        val dataOrException = DataOrException<List<Loan>, Exception>()
        try {
            dataOrException.data = getAllLoans.get().await().map { document ->
                document.toObject(Loan::class.java)
            }
        }catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}