package com.root14.hoopoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.hoopoe.data.enum.SortType
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.data.model.SortTop
import com.root14.hoopoe.data.repository.MainRepository
import com.root14.hoopoe.view.bottomsheet.MainBottomSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    lateinit var bottomSheetInstance: MainBottomSheet

    private val _notifyChanged = MutableLiveData<Boolean>(false)
    val notifyChanged: LiveData<Boolean>
        get() = _notifyChanged

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
        _notifyChanged.value = !_notifyChanged.value!!
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
        _notifyChanged.value = !_notifyChanged.value!!
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
        _notifyChanged.value = !_notifyChanged.value!!
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

    //TODO add symbol search like BTC,ADA...
    fun query(query: String): Assets {
        val dataArray = _assets.value?.data
        val resultArray = arrayListOf<AssetsData>()

        viewModelScope.launch(Dispatchers.Default) {
            if (dataArray != null) {
                for (data0 in dataArray) {
                    val name = data0.name ?: continue
                    if (name.contains(query, ignoreCase = true)) {
                        resultArray.add(data0)
                    }
                }
            }
        }
        return Assets(data = resultArray)
    }
}

