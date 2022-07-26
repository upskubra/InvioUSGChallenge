package com.example.inviousgchallenge.data.remote

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CatDataSource {
    suspend fun getCatImage(): Flow<NetworkResult<CatImageResponse>>
}