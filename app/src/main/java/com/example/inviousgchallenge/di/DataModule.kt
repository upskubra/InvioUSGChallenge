package com.example.inviousgchallenge.di

import com.example.inviousgchallenge.data.remote.ApiService
import com.example.inviousgchallenge.data.remote.CatDataSource
import com.example.inviousgchallenge.data.remote.CatDataSourceImpl
import com.example.inviousgchallenge.data.repository.cat.CatRepository
import com.example.inviousgchallenge.data.repository.cat.CatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesApiRemoteDataSource(apiService: ApiService): CatDataSource {
        return CatDataSourceImpl(apiService)
    }

    @Provides
    fun providesApiRemoteRepository(catDataSource: CatDataSource): CatRepository {
        return CatRepositoryImpl(catDataSource)
    }
}