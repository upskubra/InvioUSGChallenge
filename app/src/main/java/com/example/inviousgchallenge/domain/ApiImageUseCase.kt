package com.example.inviousgchallenge.domain

import com.example.inviousgchallenge.util.NetworkResult
import com.example.inviousgchallenge.data.model.ApiImageResponse
import com.example.inviousgchallenge.data.repository.ApiRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiImageUseCase @Inject constructor(
    private val apiRemoteRepository: ApiRemoteRepository
) {
    operator fun invoke(): Flow<NetworkResult<ApiImageResponse>> = flow {
        try {
            emit(NetworkResult.Loading())
            val apiImageItem = apiRemoteRepository.getApiImage().body()!!
            emit(NetworkResult.Success(apiImageItem))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}