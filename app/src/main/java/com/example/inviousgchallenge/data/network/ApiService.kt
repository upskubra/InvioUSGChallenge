package com.example.inviousgchallenge.data.network

import com.example.inviousgchallenge.util.Constants.API_KEY
import com.example.inviousgchallenge.data.model.ApiImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("breeds")
    suspend fun getApiImage(
        @Header("x-api-key") apiKey: String = API_KEY
    ): Response<ApiImageResponse>
}