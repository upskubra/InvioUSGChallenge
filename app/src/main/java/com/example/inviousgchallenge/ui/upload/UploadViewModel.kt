package com.example.inviousgchallenge.ui.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.data.model.UploadViewState
import com.example.inviousgchallenge.data.repository.StorageRepository
import com.example.inviousgchallenge.util.FirebaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
) : ViewModel() {
    private var _addImageStorageState = MutableStateFlow(UploadViewState())
    var addImageStorageState: StateFlow<UploadViewState> =
        _addImageStorageState.asStateFlow()


    fun addImageStorage(imageUri: Uri, description: String) = viewModelScope.launch {
        storageRepository.addImageToFirebaseStorage(imageUri, description).collect { response ->
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
                                description = description,
                            )
                        }
                        list.add(uri)
                    }
                    _addImageStorageState.value = addImageStorageState.value.copy(uriList = list)
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
    }

    fun imageUrlConsumed(uri: Uri) {

        val list = addImageStorageState.value.uriList?.toMutableList()
        list?.remove(uri)
        _addImageStorageState.value = addImageStorageState.value.copy(uriList = list)
    }
}
