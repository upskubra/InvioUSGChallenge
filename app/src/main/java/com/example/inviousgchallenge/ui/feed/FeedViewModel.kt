package com.example.inviousgchallenge.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.data.model.FirebaseViewState
import com.example.inviousgchallenge.data.repository.StorageRepository
import com.example.inviousgchallenge.util.FirebaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val storageRepository: StorageRepository) :
    ViewModel() {

    private var _feedImageState = MutableStateFlow(FirebaseViewState())
    var feedImageState: StateFlow<FirebaseViewState> = _feedImageState.asStateFlow()

    private val _authState = MutableStateFlow(FirebaseViewState())
    val authState: StateFlow<FirebaseViewState> = _authState.asStateFlow()

    suspend fun signIn() {
        withContext(Dispatchers.IO) {
            viewModelScope.launch {
                storageRepository.anonymousSignIn().collect { state ->
                    when (state) {
                        is FirebaseState.Success -> {
                            _authState.value =
                                state.data?.let {
                                    FirebaseViewState(
                                        user = it,
                                        signIn = true,
                                        isLoading = false
                                    )
                                }!!
                            getFeedImages()
                        }
                        is FirebaseState.Failure -> {
                            _authState.update {
                                it.copy(
                                    isLoading = false,
                                    signIn = false,
                                    error = it.error ?: "An unexpected error occurred"
                                )
                            }
                        }
                        is FirebaseState.Loading -> {
                            _authState.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }


    suspend fun getFeedImages() =
        withContext(Dispatchers.IO) {              // Dispatchers.IO (main-safety block)
            viewModelScope.launch {
                storageRepository.getImages().collect { result ->
                    when (result) {
                        is FirebaseState.Success -> {
                            _feedImageState.value =
                                result.data?.let {
                                    FirebaseViewState(
                                        feedImageList = it,
                                        isLoading = false
                                    )
                                }!!
                        }
                        is FirebaseState.Failure -> {
                            _feedImageState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.e ?: "An unexpected error occurred"
                                )
                            }
                        }
                        is FirebaseState.Loading -> {
                            _feedImageState.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
}