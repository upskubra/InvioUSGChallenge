package com.example.inviousgchallenge.di

import com.example.inviousgchallenge.data.firebase.storage.StorageDataSource
import com.example.inviousgchallenge.data.firebase.storage.StorageDataSourceImpl
import com.example.inviousgchallenge.data.repository.StorageRepository
import com.example.inviousgchallenge.data.repository.StorageRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabaseInstance(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageInstance(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun providesStorageDataSource(
        storage: FirebaseStorage,
        database: FirebaseFirestore
    ): StorageDataSource {
        return StorageDataSourceImpl(storage, database)
    }

    @Provides
    @Singleton
    fun providesStorageRepository(storageDataSource: StorageDataSource): StorageRepository {
        return StorageRepositoryImpl(storageDataSource)
    }

}