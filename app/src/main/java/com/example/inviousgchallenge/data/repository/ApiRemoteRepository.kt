package com.example.inviousgchallenge.data.repository

import com.example.inviousgchallenge.data.network.ApiService
import javax.inject.Inject

class ApiRemoteRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getApiImage() = apiService.getApiImage()
}
