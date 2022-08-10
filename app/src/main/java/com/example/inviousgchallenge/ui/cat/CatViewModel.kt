package com.example.inviousgchallenge.ui.cat

import android.app.Application
import com.example.inviousgchallenge.data.model.CatImageViewState
import com.example.inviousgchallenge.data.repository.cat.CatRepository
import com.example.inviousgchallenge.ui.BaseViewModel
import com.example.inviousgchallenge.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class CatViewModel @Inject constructor(
    private val catRepository: CatRepository,
    application: Application
) : BaseViewModel(application) {
    private val _catImageState = MutableStateFlow(CatImageViewState())
    val catImageState: StateFlow<CatImageViewState> = _catImageState.asStateFlow()

    suspend fun getCatImages() {
        catRepository.getCatImage().collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _catImageState.value =
                        result.data?.let {
                            CatImageViewState(
                                catList = it,
                                isLoading = false
                            )
                        }!!
                }
                is NetworkResult.Error -> {
                    _catImageState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _catImageState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }
}
