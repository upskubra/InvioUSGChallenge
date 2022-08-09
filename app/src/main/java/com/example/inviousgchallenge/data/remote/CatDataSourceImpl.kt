package com.example.inviousgchallenge.data.remote

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class CatDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) :
    CatDataSource, BaseRemoteDataSource() {
    override suspend fun getCatImage(): Flow<NetworkResult<CatImageResponse>> {
        return getResult {
            apiService.getCatImage()
        }
    }
}

