package com.example.inviousgchallenge.data.firebase.storage

import android.net.Uri
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage,
) : StorageDataSource {
    override suspend fun getImages(user: String): Flow<FirebaseState<ArrayList<Image>>> = flow {
        emit(FirebaseState.Loading)
        val result = storage.reference.child(user)
        val images = ArrayList<Image>()
        try {
            result.listAll().await().items.forEach {
                val name = it.name
                val uri = it.downloadUrl.await().toString()
                images.add(Image(name, uri))
            }
            emit(FirebaseState.Success(images))
        } catch (e: Exception) {
            emit(FirebaseState.Failure(e.localizedMessage))
            println(e.localizedMessage)
        }
    }

    override suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        description: String,
        user: String
    ) = flow {
        try {
            emit(FirebaseState.Loading)
            val downloadUrl = storage.reference
                .child(user)
                .child(description)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            emit(FirebaseState.Success(downloadUrl))
        } catch (e: Exception) {
            emit(FirebaseState.Failure(e.localizedMessage))
        }
    }
}
