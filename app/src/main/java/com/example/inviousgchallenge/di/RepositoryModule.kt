package com.example.inviousgchallenge.di

import com.example.inviousgchallenge.data.network.ApiRemoteDataSourceImpl
import com.example.inviousgchallenge.data.repository.ApiRemoteRepository
import com.example.inviousgchallenge.data.repository.ApiRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesApiRemoteRepository(apiRemoteDataSourceImpl: ApiRemoteDataSourceImpl) : ApiRemoteRepository {
        return ApiRemoteRepositoryImpl(apiRemoteDataSourceImpl)
    }
}
