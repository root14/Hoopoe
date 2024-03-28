package com.root14.hoopoe.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.root14.hoopoe.data.entity.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorite(): List<Favorite>

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(vararg favorite: Favorite)

    //TODO  add fav contains

}