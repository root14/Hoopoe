package com.root14.hoopoe.data.api

import com.root14.hoopoe.data.model.AssetById
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.data.model.Interval
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiService {
    @GET("assets")
    suspend fun getAssets(@Query("limit") param: String): Response<Assets>

    @GET("assets/{id}")
    suspend fun getAssetsById(@Path(value = "id") id: String): Response<AssetById>

    @GET("assets/{id}/history")
    suspend fun getAssetsHistoryById(
        @Path(value = "id") id: String,
        @Query("interval") interval: String
    ): Response<Interval>
}