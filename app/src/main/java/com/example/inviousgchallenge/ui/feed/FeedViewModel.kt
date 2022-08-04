package com.example.inviousgchallenge.ui.feed

import android.app.Application
import com.example.inviousgchallenge.data.model.FeedViewState
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


    suspend fun getFeedImages() =
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