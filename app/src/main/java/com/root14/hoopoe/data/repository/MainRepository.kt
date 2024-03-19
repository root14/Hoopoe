package com.root14.hoopoe.data.repository

import com.root14.hoopoe.data.api.IApiService
import com.root14.hoopoe.data.model.Assets

class MainRepository(private val iApiService: IApiService) {

    //TODO pass 100 200 500 2000 asset data to api
    suspend fun getAssets(limit: String): Assets? {
        val result = iApiService.getAssets(limit)
        return if (result.isSuccessful) {
            //cache on inMemoryRoomDb
            //if there is a data on db provide that
            result.body()!!
        } else {
            //~~~ log to somewhere
            null
        }
    }
}