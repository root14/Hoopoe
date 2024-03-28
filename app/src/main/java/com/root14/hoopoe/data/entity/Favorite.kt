package com.root14.hoopoe.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "favorites",
    indices = [Index(value = ["assetName"], unique = true)])
data class Favorite(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "assetName") val assetName: String
) {
}