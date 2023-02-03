package com.derich.bigfoot.ui.common.firestorequeries

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Singleton

@Singleton
class FirestoreQueries {
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun queryAllTransactions(): Query = firebaseFirestore
        .collectionGroup("allTransactions")
        .orderBy("transactionDate", Query.Direction.DESCENDING)

    //query all loans from DB
    fun queryAllLoans() = firebaseFirestore
        .collectionGroup("allLoans")
        .orderBy("dateLoaned", Query.Direction.DESCENDING)
    fun queryAllMemberDetails() = firebaseFirestore
        .collectionGroup("allMembers")
        .orderBy("totalAmount", Query.Direction.DESCENDING)
}
//    fun queryTransactionsByUsername(fullNames: String) = firebaseFirestore
//        .collectionGroup("allTransactions").whereEqualTo("depositFor",fullNames)
//        .orderBy("transactionDate",Query.Direction.DESCENDING)

//    fun queryAllContributions() = firebaseFirestore
//        .collectionGroup("Totals")
//        .orderBy("totalAmount", Query.Direction.DESCENDING)

//    fun queryMemberDetails(phoneNumber: String) = firebaseFirestore
//        .collectionGroup("allMembers")
//        .whereEqualTo("phoneNumber", phoneNumber)