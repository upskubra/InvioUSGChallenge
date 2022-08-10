package com.example.inviousgchallenge.data.repository.cat

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCatImage(): Flow<NetworkResult<CatImageResponse>>
}
