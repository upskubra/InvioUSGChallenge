package com.example.inviousgchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.Util.NetworkResult
import com.example.inviousgchallenge.data.model.ApiImageItemListState
import com.example.inviousgchallenge.domain.ApiImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class ApiImagesViewModel @Inject constructor(private val apiImageUseCase: ApiImageUseCase) :
    ViewModel() {

    private val _apiImageState = MutableStateFlow(ApiImageItemListState())
    val apiImageState: StateFlow<ApiImageItemListState> = _apiImageState.asStateFlow()


    init {
        getApiImages()
    }

    fun getApiImages() {
        apiImageUseCase().onEach { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _apiImageState.value =
                        result.data?.let { ApiImageItemListState(apiImageItemList = it, isLoading = false) }!!
                }
                is NetworkResult.Error -> {
                    _apiImageState.value = ApiImageItemListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is NetworkResult.Loading -> {
                    _apiImageState.value = ApiImageItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
