package com.root14.hoopoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.hoopoe.data.enum.SortType
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    //TODO handle slow bandwidth loading state
    private val _assets = MutableLiveData<Assets>()
    val assets: LiveData<Assets>
        get() = _assets

    fun updateCoinList(): LiveData<Assets> {
        viewModelScope.launch {
            mainRepository.getUsers().let {
                _assets.value = it
            }
        }
        return _assets
    }

    private var sortTypePrice: SortType = SortType.NOT_SORTED
    fun sortPrice() {
        sortTypePrice = if (sortTypePrice == SortType.DESCENDING) {
            _assets.value?.data?.sortBy { price ->
                price.priceUsd?.takeWhile {
                    it != '.'
                }?.toInt()
            }
            SortType.ASCENDING
        } else {
            _assets.value?.data?.sortByDescending { price ->
                price.priceUsd?.takeWhile {
                    it != '.'
                }?.toInt()
            }
            SortType.DESCENDING
        }
    }

    private var sortType7d: SortType = SortType.NOT_SORTED
    fun sort24d() {
        sortType7d = if (sortType7d == SortType.DESCENDING) {
            _assets.value?.data?.sortBy { price ->
                price.changePercent24Hr?.takeWhile {
                    it != '.'
                }?.toInt()
            }
            SortType.ASCENDING
        } else {
            _assets.value?.data?.sortByDescending { price ->
                price.changePercent24Hr?.takeWhile {
                    it != '.'
                }?.toInt()
            }
            SortType.DESCENDING
        }
    }
}