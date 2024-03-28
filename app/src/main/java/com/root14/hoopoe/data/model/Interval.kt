package com.root14.hoopoe.data.model

import com.google.gson.annotations.SerializedName


data class Interval(
    @SerializedName("data") var data: ArrayList<IntervalData> = arrayListOf(),
    @SerializedName("time") var timestamp: String? = null
)