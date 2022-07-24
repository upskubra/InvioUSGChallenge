package com.example.inviousgchallenge.data.repository


import com.example.inviousgchallenge.data.model.ApiImageResponse
import retrofit2.Response

interface ApiRemoteRepository {

    suspend fun getApiImage(): Response<ApiImageResponse>

}
