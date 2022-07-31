package com.example.inviousgchallenge.data.firebase.storage

import android.net.Uri
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.Constants.UID
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val database: FirebaseFirestore
) : StorageDataSource {
    override suspend fun getImages(): Flow<FirebaseState<ArrayList<Image>>> = flow {
        emit(FirebaseState.Loading)
        val result = database.collection("image")
            .whereEqualTo("uid", UID)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
        if (result.isSuccessful) {
            val images = arrayListOf<Image>()
            for (document in result.result) {
                val image = document.toObject(Image::class.java)
                images.add(image)
            }
            emit(FirebaseState.Success(images))
        } else {
            emit(FirebaseState.Failure(result.exception?.localizedMessage))
        }
    }

    override suspend fun addImageToFirebaseStorage(imageUri: Uri) = flow {
        try {
            emit(FirebaseState.Loading)
            val downloadUrl = storage.reference
                .child("images")
                .child("${UID}_${System.currentTimeMillis()}")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            emit(FirebaseState.Success(downloadUrl))
        } catch (e: Exception) {
            emit(FirebaseState.Failure(e.localizedMessage))
        }
    }
}
