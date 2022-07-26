package com.example.inviousgchallenge.data.remote

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("breeds")
    suspend fun getCatImage(
        @Header("x-api-key") apiKey: String = API_KEY
    ): Response<CatImageResponse>
}