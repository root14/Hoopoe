package com.root14.hoopoe.data.api

import com.root14.hoopoe.data.model.Assets
import retrofit2.Response
import retrofit2.http.GET

interface IApiService {
    @GET("assets")
    suspend fun getAssets(): Response<Assets>
}