package com.example.inviousgchallenge.data.repository.cat

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.data.remote.CatDataSource
import com.example.inviousgchallenge.data.repository.cat.CatRepository
import com.example.inviousgchallenge.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(private val catDataSource: CatDataSource) :
    CatRepository {
    override suspend fun getCatImage(): Flow<NetworkResult<CatImageResponse>> =
        catDataSource.getCatImage()
}