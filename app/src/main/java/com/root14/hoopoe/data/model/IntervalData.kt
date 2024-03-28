package com.root14.hoopoe.data.model

import com.google.gson.annotations.SerializedName


data class IntervalData(
    @SerializedName("priceUsd") var priceUsd: String? = null,
    @SerializedName("time") var time: String? = null
)