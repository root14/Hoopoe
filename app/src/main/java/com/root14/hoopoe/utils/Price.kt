package com.root14.hoopoe.utils

fun String.convertPriceFormat(): String {
    val amount = this.toDoubleOrNull() ?: return this
    return String.format("$%,.2f", amount)
}

fun String.convertMarketCap(): String {
    val amount = this.toDoubleOrNull() ?: return this

    val trillion = 1000000000000
    val billion = 1000000000
    val million = 1000000

    val formattedAmount = when {
        amount >= trillion -> String.format("$%,.2f T", amount / trillion)
        amount >= billion -> String.format("$%,.2f B", amount / billion)
        amount >= million -> String.format("$%,.2f M", amount / million)
        else -> String.format("$%,.2f", amount)
    }

    return formattedAmount
}

fun String.convertPriceChange(): String {
    val amount = this.toDoubleOrNull() ?: return this
    val formattedAmount = String.format("%.2f", amount)
    return if (amount >= 0) {
        "%+$formattedAmount"
    } else {
        "%$formattedAmount"
    }
}
