package com.example.inviousgchallenge.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.data.model.FeedViewState
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

    private var _feedImageState = MutableStateFlow(FeedViewState())
    var feedImageState: StateFlow<FeedViewState> = _feedImageState.asStateFlow()

    private val _authState = MutableStateFlow(FeedViewState())
    val authState: StateFlow<FeedViewState> = _authState.asStateFlow()

    suspend fun signIn() {
        withContext(Dispatchers.IO) {
            viewModelScope.launch {
                storageRepository.anonymousSignIn().collect { state ->
                    when (state) {
                        is FirebaseState.Success -> {
                            _authState.value =
                                state.data?.let {
                                    FeedViewState(
                                        user = it,
                                        signIn = true,
                                        loading = false
                                    )
                                }!!
                            getFeedImages()
                        }
                        is FirebaseState.Failure -> {
                            _authState.update {
                                it.copy(
                                    loading = false,
                                    signIn = false,
                                    error = it.error
                                )
                            }
                        }
                        is FirebaseState.Loading -> {
                            _authState.update {
                                it.copy(loading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    suspend fun getFeedImages() =
        viewModelScope.launch {
            storageRepository.getImages().collect { result ->
                when (result) {
                    is FirebaseState.Success -> {
                        result.data?.let { list ->
                            _feedImageState.update {
                                it.copy(
                                    imageList = list,
                                    loading = false,
                                )
                            }
                        }

                    }
                    is FirebaseState.Failure -> {
                        _feedImageState.update {
                            it.copy(
                                loading = false,
                                error = result.e ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is FirebaseState.Loading -> {
                        _feedImageState.update {
                            it.copy(loading = true)
                        }
                    }
                }
            }
        }
}