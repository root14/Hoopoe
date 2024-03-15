package com.root14.hoopoe.data.repository

import com.root14.hoopoe.data.api.IApiService
import com.root14.hoopoe.data.model.Assets

class MainRepository(private val iApiService: IApiService) {

    suspend fun getUsers(): Assets? {
        val result = iApiService.getAssets()
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