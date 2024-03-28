package com.root14.hoopoe.di

import android.content.Context
import androidx.room.Room
import com.root14.hoopoe.data.api.IApiService
import com.root14.hoopoe.data.database.AppDataBase
import com.root14.hoopoe.data.repository.MainRepository
import com.root14.hoopoe.utils.Url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    fun provideBaseUrl() = Url.baseUrl

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IApiService =
        retrofit.create(IApiService::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(apiService: IApiService) = MainRepository(apiService)

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, "app-database").build()

    @Provides
    @Singleton
    fun provideFavoriteDao(appDataBase: AppDataBase) = appDataBase.favoriteDao()
}