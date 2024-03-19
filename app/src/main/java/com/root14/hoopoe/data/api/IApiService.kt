package com.root14.hoopoe.data.api

import com.root14.hoopoe.data.model.Assets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("assets")
    suspend fun getAssets(@Query("limit") param: String): Response<Assets>
}