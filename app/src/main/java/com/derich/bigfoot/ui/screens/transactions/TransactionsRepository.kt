package com.derich.bigfoot.ui.screens.transactions

import com.derich.bigfoot.ui.common.firestorequeries.FirestoreQueries
import com.derich.bigfoot.ui.data.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class TransactionsRepository {
    private val firestoreQueries: FirestoreQueries = FirestoreQueries()
    private val getAllTransactions : Query = firestoreQueries.queryAllTransactions()
  suspend fun getAllTransactionsFromFirestone(): DataOrException<List<Transactions>, Exception> {
      val dataOrException = DataOrException<List<Transactions>, Exception>()
      try {
          dataOrException.data = getAllTransactions.get().await().map { document ->
              document.toObject(Transactions::class.java)
          }
      }catch (e: FirebaseFirestoreException) {
          dataOrException.e = e
      }
      return dataOrException
  }
}