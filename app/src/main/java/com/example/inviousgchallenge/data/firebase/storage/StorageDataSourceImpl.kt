package com.example.inviousgchallenge.data.firebase.storage

import android.net.Uri
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.util.Constants.UID
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
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
        val result = storage.reference.child("images")


        try {
            val images = arrayListOf<Image>()
            result.listAll()
                .addOnSuccessListener { (_, prefixes) ->
                    prefixes.forEach { prefix ->
                        val image = prefix.child("${prefix.name}.png")

                        image.downloadUrl.addOnSuccessListener {
                            images.add(Image(prefix.name, it.toString()))
                        }
                    }
                }

            emit(FirebaseState.Success(images))
        } catch (e: Exception) {
            emit(FirebaseState.Failure(e.localizedMessage))
            println(e.localizedMessage)
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
