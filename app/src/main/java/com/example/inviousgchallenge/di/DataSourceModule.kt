package com.example.inviousgchallenge.di

import com.example.inviousgchallenge.data.network.ApiRemoteDataSourceImpl
import com.example.inviousgchallenge.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun providesApiRemoteDataSource(apiService: ApiService): ApiRemoteDataSourceImpl {
        return ApiRemoteDataSourceImpl(apiService)
    }
}
