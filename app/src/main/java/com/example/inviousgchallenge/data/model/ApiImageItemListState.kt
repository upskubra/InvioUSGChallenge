package com.example.inviousgchallenge.data.model

data class ApiImageItemListState(
    val isLoading: Boolean = false,
    val apiImageItemList: ApiImageResponse = ApiImageResponse(),
    val error: String = ""
)
