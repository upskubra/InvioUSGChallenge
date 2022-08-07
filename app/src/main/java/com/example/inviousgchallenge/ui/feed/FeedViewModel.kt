package com.example.inviousgchallenge.ui.feed

import android.app.Application
import com.example.inviousgchallenge.data.model.ViewStates.DeleteViewState
import com.example.inviousgchallenge.data.model.ViewStates.FeedViewState
import com.example.inviousgchallenge.data.repository.StorageRepository
import com.example.inviousgchallenge.ui.BaseViewModel
import com.example.inviousgchallenge.util.FirebaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    application: Application
) : BaseViewModel(application) {

    private var _feedImageState = MutableStateFlow(FeedViewState())
    var feedImageState: StateFlow<FeedViewState> = _feedImageState.asStateFlow()

    private var _deleteImageState = MutableStateFlow(DeleteViewState())
    var deleteImageState: StateFlow<DeleteViewState> = _deleteImageState.asStateFlow()


    suspend fun getFeedImages(user: String) =
        storageRepository.getImages(user).collect { result ->
            when (result) {
                is FirebaseState.Success -> {
                    result.data?.let { list ->
                        _feedImageState.update {
                            it.copy(
                                imageList = list,
                                loading = false,
                                user = user
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

    suspend fun deleteImage(user: String, imageId: String) =
        storageRepository.deleteImage(user, imageId).collect { result ->
            when (result) {
                is FirebaseState.Success -> {
                    _deleteImageState.update {
                        it.copy(
                            isDeleted = true,
                            loading = false,
                            user = user,
                            imageId = imageId
                        )
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