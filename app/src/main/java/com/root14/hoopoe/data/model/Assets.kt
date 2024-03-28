package com.root14.hoopoe.data.model

import com.google.gson.annotations.SerializedName

data class Assets(
    @SerializedName("data") var data: ArrayList<AssetsData> = arrayListOf(),
    @SerializedName("timestamp") var timestamp: String? = null
)