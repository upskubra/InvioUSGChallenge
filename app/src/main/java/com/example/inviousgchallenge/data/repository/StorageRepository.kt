package com.example.inviousgchallenge.data.repository

import android.net.Uri
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun getImages(): Flow<FirebaseState<ArrayList<Image>>>
    suspend fun addImageToFirebaseStorage(imageUri: Uri): Flow<FirebaseState<Uri>>
    fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>>
}