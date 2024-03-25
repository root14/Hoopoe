package com.root14.hoopoe.data.model

import com.google.gson.annotations.SerializedName


data class AssetById(
    @SerializedName("data") var data: AssetsData? = AssetsData(),
    @SerializedName("timestamp") var timestamp: String? = null
)