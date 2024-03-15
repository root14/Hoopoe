package com.root14.hoopoe.di

import com.root14.hoopoe.data.api.IApiService
import com.root14.hoopoe.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.coincap.io/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IApiService =
        retrofit.create(IApiService::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(apiService: IApiService) = MainRepository(apiService)
}