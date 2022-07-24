package com.example.inviousgchallenge.data.repository

import com.example.inviousgchallenge.data.model.ApiImageResponse
import com.example.inviousgchallenge.data.network.ApiRemoteDataSourceImpl
import retrofit2.Response
import javax.inject.Inject

class ApiRemoteRepositoryImpl @Inject constructor(private val apiRemoteDataSourceImpl: ApiRemoteDataSourceImpl) :
    ApiRemoteRepository {
    override suspend fun getApiImage(): Response<ApiImageResponse> = apiRemoteDataSourceImpl.getApiImage()

}