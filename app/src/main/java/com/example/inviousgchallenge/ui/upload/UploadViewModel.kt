package com.example.inviousgchallenge.ui.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.data.model.FirebaseViewState
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
    private var _addImageStorageState = MutableStateFlow(FirebaseViewState())
    var addImageStorageState: StateFlow<FirebaseViewState> =
        _addImageStorageState.asStateFlow()


    fun addImageStorage(imageUri: Uri) = viewModelScope.launch {
        storageRepository.addImageToFirebaseStorage(imageUri).collect { response ->
            when (response) {
                is FirebaseState.Loading ->
                    _addImageStorageState.update {
                        it.copy(isLoading = true)

                    }
                is FirebaseState.Success ->
                    response.data?.let {
                        FirebaseViewState(
                            isSuccess = true,
                            uri = it,
                            isLoading = false
                        )
                    }
                is FirebaseState.Failure ->
                    _addImageStorageState.update {
                        it.copy(isLoading = false, error = "Error")
                    }
            }
        }
    }
}

