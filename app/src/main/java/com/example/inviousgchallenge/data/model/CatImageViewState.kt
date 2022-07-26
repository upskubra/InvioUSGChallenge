package com.example.inviousgchallenge.data.model

data class CatImageViewState(
    val isLoading: Boolean = false,
    val catList: CatImageResponse = CatImageResponse(),
    val error: String = ""
)
