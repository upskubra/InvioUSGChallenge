package com.example.inviousgchallenge.data.remote

import com.example.inviousgchallenge.data.model.CatImageResponse
import com.example.inviousgchallenge.data.remote.ApiService
import com.example.inviousgchallenge.data.remote.CatDataSource
import com.example.inviousgchallenge.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class CatDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) :
    CatDataSource {
    override suspend fun getCatImage(): Flow<NetworkResult<CatImageResponse>> = flow {
        try {
            emit(NetworkResult.Loading())
            val response = apiService.getCatImage()
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}

