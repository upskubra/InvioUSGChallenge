package com.example.inviousgchallenge.data.repository

import android.net.Uri
import com.example.inviousgchallenge.data.firebase.auth.AuthDataSource
import com.example.inviousgchallenge.data.firebase.storage.StorageDataSource
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageDataSource: StorageDataSource,
    private val authDataSource: AuthDataSource
) :
    StorageRepository {
    override suspend fun getImages(): Flow<FirebaseState<ArrayList<Image>>> {
        return storageDataSource.getImages()
    }

    override suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
    ): Flow<FirebaseState<Uri>> {
        return storageDataSource.addImageToFirebaseStorage(imageUri)
    }

    override fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>> {
        return authDataSource.anonymousSignIn()
    }
}
