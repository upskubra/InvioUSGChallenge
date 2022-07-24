package com.example.inviousgchallenge.data.network

import com.example.inviousgchallenge.data.model.ApiImageResponse
import retrofit2.Response

interface ApiRemoteDataSource {
    suspend fun getApiImage(): Response<ApiImageResponse>

}