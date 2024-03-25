package com.root14.hoopoe.data.repository

import com.root14.hoopoe.data.api.IApiService
import com.root14.hoopoe.data.model.AssetById
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.Interval

class MainRepository(private val iApiService: IApiService) {

    suspend fun getAssets(limit: String): Assets? {
        val result = iApiService.getAssets(limit)
        return if (result.isSuccessful) {
            //cache on inMemoryRoomDb
            //if there is a data on db provide that
            result.body()
        } else {
            //~~~ log to somewhere
            null
        }
    }

    suspend fun getAssetsById(id: String): AssetById? {
        val result = iApiService.getAssetsById(id)
        return if (result.isSuccessful) {
            //cache on inMemoryRoomDb
            //if there is a data on db provide that
            result.body()
        } else {
            //~~~ log to somewhere
            null
        }
    }

    suspend fun getAssetsHistoryById(id: String, interval: String): Interval? {
        val result = iApiService.getAssetsHistoryById(id = id, interval = interval)
        return if (result.isSuccessful) {
            //cache on inMemoryRoomDb
            //if there is a data on db provide that
            result.body()
        } else {
            //~~~ log to somewhere
            null
        }
    }


}