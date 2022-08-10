package com.example.inviousgchallenge.ui.upload

import android.app.Application
import android.net.Uri
import com.example.inviousgchallenge.data.model.ViewStates.UploadViewState
import com.example.inviousgchallenge.data.repository.storage.StorageRepository
import com.example.inviousgchallenge.ui.BaseViewModel
import com.example.inviousgchallenge.util.FirebaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val storageRepository: StorageRepository, application: Application,
) : BaseViewModel(application) {
    private var _addImageStorageState = MutableStateFlow(UploadViewState())
    var addImageStorageState: StateFlow<UploadViewState> =
        _addImageStorageState.asStateFlow()

    suspend fun addImageStorage(imageUri: Uri, description: String, user: String) =
        storageRepository.addImageToFirebaseStorage(imageUri, description, user)
            .collect { response ->
                when (response) {
                    is FirebaseState.Success -> {
                        val list =
                            _addImageStorageState.value.uriList?.toMutableList() ?: mutableListOf()
                        response.data?.let { uri ->
                            _addImageStorageState.update {
                                it.copy(
                                    loading = false,
                                    success = true,
                                    uri = uri,
                                    user = user,
                                    description = description,
                                )
                            }
                            list.add(uri)
                        }
                        _addImageStorageState.value =
                            addImageStorageState.value.copy(uriList = list)
                    }
                    is FirebaseState.Loading ->
                        _addImageStorageState.update {
                            it.copy(loading = true)

                        }
                    is FirebaseState.Failure ->
                        _addImageStorageState.update {
                            it.copy(loading = false, error = "Error")
                        }
                }
            }

    fun imageUrlConsumed(uri: Uri) {

        val list = addImageStorageState.value.uriList?.toMutableList()
        list?.remove(uri)
        _addImageStorageState.value = addImageStorageState.value.copy(uriList = list)
    }
}


