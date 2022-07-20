package com.example.inviousgchallenge.domain

import com.example.inviousgchallenge.data.model.RequestState
import com.example.inviousgchallenge.data.repository.ApiRemoteRepositoryImpl
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetApiImageUseCase @Inject constructor(private val apiRemoteRepositoryImpl: ApiRemoteRepositoryImpl) {
    suspend operator fun invoke() = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(apiRemoteRepositoryImpl.getApiImage()))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }


}