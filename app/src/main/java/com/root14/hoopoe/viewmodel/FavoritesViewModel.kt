package com.root14.hoopoe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.hoopoe.data.dao.FavoriteDao
import com.root14.hoopoe.data.entity.Favorite
import com.root14.hoopoe.data.model.Interval
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoriteDao: FavoriteDao) : ViewModel() {
    private val _favorite = MutableLiveData<List<Favorite>>()
    val favorite: LiveData<List<Favorite>>
        get() = _favorite

    fun getAllFavorites(): LiveData<List<Favorite>> {
        viewModelScope.launch(Dispatchers.IO) {
            _favorite.postValue(favoriteDao.getAllFavorite())
        }

        return favorite
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) { favoriteDao.insertFavorite(favorite) }
    }

    fun deleteFavorite(assetName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.deleteFavorite(assetName)
        }
    }

    //TODO add fav contains
}