package com.derich.bigfoot.ui.common.firestorequeries

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Singleton

@Singleton
class FirestoreQueries {
    fun queryAllTransactions(): Query = FirebaseFirestore.getInstance()
        .collectionGroup("allTransactions")
        .orderBy("transactionDate", Query.Direction.DESCENDING)

    fun queryTransactionsByUsername(fullNames: String) = FirebaseFirestore.getInstance()
        .collectionGroup("allTransactions").whereEqualTo("depositFor",fullNames)
        .orderBy("transactionDate",Query.Direction.DESCENDING)

    fun queryAllContributions() = FirebaseFirestore.getInstance()
        .collectionGroup("Totals")
        .orderBy("totalAmount", Query.Direction.DESCENDING)
}