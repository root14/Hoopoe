package com.root14.hoopoe.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun String.timeStampToDate(): LocalDateTime? {
    val instant: Instant = Instant.ofEpochSecond(this.toLong())
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}
