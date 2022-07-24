package com.example.inviousgchallenge.data.network

import com.example.inviousgchallenge.data.model.ApiImageResponse
import retrofit2.Response
import javax.inject.Inject

class ApiRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) :
    ApiRemoteDataSource {
    override suspend fun getApiImage(): Response<ApiImageResponse> {
        return apiService.getApiImage()
    }
}

