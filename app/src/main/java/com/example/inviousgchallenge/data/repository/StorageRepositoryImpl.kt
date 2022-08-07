package com.example.inviousgchallenge.data.repository

import android.net.Uri
import com.example.inviousgchallenge.data.firebase.storage.StorageDataSource
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.FirebaseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageDataSource: StorageDataSource,
) :
    StorageRepository {
    override suspend fun getImages(user: String): Flow<FirebaseState<ArrayList<Image>>> {
        return storageDataSource.getImages(user)
    }

    override suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        description: String,
        user: String
    ): Flow<FirebaseState<Uri>> {
        return storageDataSource.addImageToFirebaseStorage(imageUri, description, user)
    }

    override suspend fun deleteImage(
        user: String,
        imageId: String
    ): Flow<FirebaseState<Boolean>> {
        return storageDataSource.deleteImage(user, imageId)
    }
}
