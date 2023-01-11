package com.derich.bigfoot.ui.data

import com.derich.bigfoot.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideMainActivity(): MainActivity = MainActivity.getInstance() as MainActivity

    @Provides
    @Singleton
    fun provideQueryTransactionsByName() = FirebaseFirestore.getInstance()
        .collectionGroup("Totals")
        .orderBy("totalAmount", Query.Direction.DESCENDING)
    //db.collectionGroup("Totals").orderBy("totalAmount", Query.Direction.DESCENDING)
}