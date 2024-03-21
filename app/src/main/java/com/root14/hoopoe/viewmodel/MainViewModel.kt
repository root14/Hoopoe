package com.root14.hoopoe.viewmodel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.hoopoe.data.enum.SortType
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.data.model.SortTop
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

    fun updateCoinList(limit: String): LiveData<Assets> {
        viewModelScope.launch {
            mainRepository.getAssets(limit).let {
                _assets.value = it
            }
        }
        return _assets
    }

    private var sortTypePrice: SortType = SortType.NOT_SORTED
    fun sortPrice() {
        sortTypePrice = if (sortTypePrice == SortType.DESCENDING) {
            _assets.value?.data?.sortBy { assetsData ->
                assetsData.priceUsd?.takeWhile {
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
            _assets.value?.data?.sortBy { assetsData ->
                assetsData.changePercent24Hr?.takeWhile {
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

    private var sortTypeRank: SortType = SortType.NOT_SORTED
    fun sortRank() {
        sortTypeRank = if (sortTypeRank == SortType.DESCENDING) {
            _assets.value?.data?.sortBy { assetData ->
                assetData.rank?.toInt()
            }
            SortType.ASCENDING
        } else {
            _assets.value?.data?.sortByDescending { assetData ->
                assetData.rank?.toInt()
            }
            SortType.DESCENDING
        }
    }

    private val _bottomSheetSortList = MutableLiveData<List<SortTop>>().apply {
        postValue(
            arrayListOf(
                SortTop("Top 100", true, "100"),
                SortTop("Top 200", false, "200"),
                SortTop("Top 500", false, "500"),
                SortTop("All Coins", false, "2000")
            )
        )
    }
    val bottomSheetSortList: LiveData<List<SortTop>>
        get() = _bottomSheetSortList

    fun selectTop(selectedIndex: Int) {
        _bottomSheetSortList.value?.forEach {
            it.selected = false
        }

        viewModelScope.launch {
            mainRepository.getAssets(_bottomSheetSortList.value!![selectedIndex].value).let {
                _assets.value = it
            }
            _bottomSheetSortList.value?.get(selectedIndex)?.selected = true
        }
    }

    //search query
    //must just return query items
    //do it on background thread
    fun query(query: String): Boolean? {
        val data0 = assets.value?.data
        val data = AssetsData(symbol = "BTC")
        return data0?.conta(data)

    }
}