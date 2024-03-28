package com.root14.hoopoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.hoopoe.data.model.AssetById
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.data.model.ChangeRate
import com.root14.hoopoe.data.model.Interval
import com.root14.hoopoe.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {


    private var _interval = MutableLiveData<Interval>()
    val interval: LiveData<Interval>
        get() = _interval

    fun getChartIntervalData(id: String, period: Int): LiveData<Interval> {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAssetsHistoryById(id = id, interval = "d1").let { interval ->
                withContext(Dispatchers.Default) {
                    val result = calculateHistoryData(interval!!, period)
                    withContext(Dispatchers.Main) {
                        _interval.value = result
                    }
                }
            }
        }
        return interval
    }

    private fun calculateHistoryData(interval: Interval, period: Int): Interval {
        val data = interval.data
        val result = ArrayList(data.subList((data.size - period), data.size))
        return Interval(data = result)
    }


    //TODO handle slow bandwidth loading state
    private val _assets = MutableLiveData<AssetById>()
    val assets: LiveData<AssetById>
        get() = _assets

    fun getAssetById(id: String): LiveData<AssetById> {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAssetsById(id).let {
                withContext(Dispatchers.Main) {
                    _assets.value = it
                }
            }
        }
        return _assets
    }

    private val _assetsList = MutableLiveData<ArrayList<AssetsData>>()
    val assetsList: LiveData<ArrayList<AssetsData>>
        get() = _assetsList

    fun getAssetById(vararg ids: String): LiveData<ArrayList<AssetsData>> {
        viewModelScope.launch(Dispatchers.IO) {
            val dataList = arrayListOf<AssetsData>()
            ids.forEach { id ->
                mainRepository.getAssetsById(id).let { assetById ->
                    assetById?.data?.let {
                        dataList.add(it)
                    }
                }
            }
            _assetsList.postValue(dataList)
        }

        return assetsList
    }

    private val _changeRate = MutableLiveData<ChangeRate>()
    val changeRate: LiveData<ChangeRate>
        get() = _changeRate

    //API has 1 day delay
    //get asset history data
    //1 week =7, 1 month=30, 1 year=364
    fun getIntervalData(assetId: String): LiveData<ChangeRate> {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAssetsHistoryById(id = assetId, interval = "d1").let { dataDaily ->
                withContext(Dispatchers.Default) {
                    val change7d = calculateChangePercentage(dataDaily!!, 7)
                    val change1m = calculateChangePercentage(dataDaily, 30)
                    val change1y = calculateChangePercentage(dataDaily, 364)
                    val change1h = _assets.value?.data?.changePercent24Hr
                    withContext(Dispatchers.Main) {
                        _changeRate.value = ChangeRate(
                            change1d = change1h.toString(),
                            change7d = change7d.toString(),
                            change1m = change1m.toString(),
                            change1y = change1y.toString()
                        )
                    }
                }
            }
        }
        return changeRate
    }

    private fun calculateChangePercentage(interval: Interval, period: Int): Double {
        val data = interval.data
        val a0 = data.subList((data.size - period), data.size)
        val last = a0.last().priceUsd!!.toDouble()
        val first = a0.first().priceUsd!!.toDouble()
        return (last - first) / first * 100
    }
}