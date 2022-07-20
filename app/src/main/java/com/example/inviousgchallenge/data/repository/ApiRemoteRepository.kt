package com.example.inviousgchallenge.data.repository

import com.example.inviousgchallenge.data.model.ApiImageResponse

interface ApiRemoteRepository {
    suspend fun getApiImage() : ApiImageResponse
}
