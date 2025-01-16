package com.calberto_barbosa_jr.firebasehub

import com.calberto_barbosa_jr.firebasehub.repository.FirestoreRepository
import com.calberto_barbosa_jr.firebasehub.repository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirestoreRepository {
        return FirestoreRepositoryImpl(db, auth)
    }
}
