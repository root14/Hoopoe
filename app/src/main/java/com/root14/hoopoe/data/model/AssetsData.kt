package com.root14.hoopoe.data.model

import com.google.gson.annotations.SerializedName

data class AssetsData(
    @SerializedName("id") var id: String? = "",
    @SerializedName("rank") var rank: String? = "",
    @SerializedName("symbol") var symbol: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("supply") var supply: String? = "",
    @SerializedName("maxSupply") var maxSupply: String? = "",
    @SerializedName("marketCapUsd") var marketCapUsd: String? = "",
    @SerializedName("volumeUsd24Hr") var volumeUsd24Hr: String? = "",
    @SerializedName("priceUsd") var priceUsd: String? = "",
    @SerializedName("changePercent24Hr") var changePercent24Hr: String? = "",
    @SerializedName("vwap24Hr") var vwap24Hr: String? = ""
)