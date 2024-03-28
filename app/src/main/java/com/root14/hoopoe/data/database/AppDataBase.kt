package com.root14.hoopoe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.root14.hoopoe.data.dao.FavoriteDao
import com.root14.hoopoe.data.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}