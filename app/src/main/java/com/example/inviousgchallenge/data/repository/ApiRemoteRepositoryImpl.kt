package com.example.inviousgchallenge.data.repository

import com.example.inviousgchallenge.data.model.ApiImageResponse
import com.example.inviousgchallenge.data.network.ApiService
import javax.inject.Inject

class ApiRemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ApiRemoteRepository {
    override suspend fun getApiImage(): ApiImageResponse = apiService.getApiImage()

}