package com.root14.hoopoe.utils

class Url {
    companion object {
        const val baseUrl = "https://api.coincap.io/v2/"
        fun getIconUrl(symbol: String): String {
            return "https://assets.coincap.io/assets/icons/$symbol@2x.png"
        }
    }
}