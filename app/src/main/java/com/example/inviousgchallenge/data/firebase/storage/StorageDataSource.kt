package com.example.inviousgchallenge.data.firebase.storage

import android.net.Uri
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.FirebaseState
import kotlinx.coroutines.flow.Flow

interface StorageDataSource {
    suspend fun getImages(user: String): Flow<FirebaseState<ArrayList<Image>>>
    suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        description: String,
        user: String
    ): Flow<FirebaseState<Uri>>

    suspend fun deleteImage(
        user: String, imageId: String
    ): Flow<FirebaseState<Boolean>>
}
