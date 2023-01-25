package com.derich.bigfoot.ui.common.firestorequeries

import com.derich.bigfoot.ui.screens.home.Contributions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Singleton

@Singleton
class FirestoreQueries {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun queryAllTransactions(): Query = db
        .collectionGroup("allTransactions")
        .orderBy("transactionDate", Query.Direction.DESCENDING)

    fun queryTransactionsByUsername(fullNames: String) = db
        .collectionGroup("allTransactions").whereEqualTo("depositFor",fullNames)
        .orderBy("transactionDate",Query.Direction.DESCENDING)

    fun queryAllContributions() = db
        .collectionGroup("Totals")
        .orderBy("totalAmount", Query.Direction.DESCENDING)
    //get contribution info of specific user
    fun queryMemberContributions(userName:String) = db
        .collectionGroup("Totals")
        .whereEqualTo("Name", userName)
    //query all loans from DB
    fun queryAllLoans() = db
        .collectionGroup("allLoans")
        .orderBy("dateLoaned", Query.Direction.DESCENDING)
    //update the contributions list when user changes their prof picture
    fun updateContributionsList(username: String, contribution:Contributions)= db.collection("TotalContributions")
        .document("allMembersTotals")
        .collection("Totals")
        .document(username)
        .set(contribution)
}