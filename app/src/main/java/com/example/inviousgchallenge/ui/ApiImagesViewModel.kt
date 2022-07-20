package com.example.inviousgchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inviousgchallenge.data.model.ApiImageResponse
import com.example.inviousgchallenge.data.model.RequestState
import com.example.inviousgchallenge.domain.GetApiImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiImagesViewModel @Inject constructor(private val getApiImageUseCase: GetApiImageUseCase) :
    ViewModel() {
    private val _apiImageState = MutableStateFlow<RequestState<ApiImageResponse>?>(null)
    val apiImageState: StateFlow<RequestState<ApiImageResponse>?> = _apiImageState

    fun getApiImages() =
        viewModelScope.launch {
            getApiImageUseCase.invoke().collect() {
                _apiImageState.value = it
            }
        }


}
