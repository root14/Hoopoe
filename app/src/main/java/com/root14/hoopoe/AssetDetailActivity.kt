package com.root14.hoopoe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.root14.hoopoe.data.WebSocketHelper
import com.root14.hoopoe.data.entity.Favorite
import com.root14.hoopoe.data.model.ChangeRate
import com.root14.hoopoe.data.model.Interval
import com.root14.hoopoe.databinding.ActivityAssetDetailBinding
import com.root14.hoopoe.viewmodel.DetailViewModel
import com.root14.hoopoe.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AssetDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityAssetDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var changeRate = ChangeRate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val assetId: String? = bundle?.getString("assetId")
        val assetName: String? = bundle?.getString("assetName")

        favoritesViewModel.getAllFavorites().observe(this) { it ->
            val isFav = it.filter { it.assetName == assetName }.size == 1
            if (isFav) {
                val buttonFav = binding.topAppBar.menu.findItem(R.id.favorite)
                buttonFav.setIcon(R.drawable.baseline_favorite_24_filled)
            }
        }

        detailViewModel.getAssetById(assetName.toString()).observe(this) { assetById ->
            binding.asset = assetById
        }

        //calculate change rate
        detailViewModel.getIntervalData(assetName.toString()).observe(this) {
            changeRate = it
            //default loading value
            binding.btn1m.performClick()
        }

        binding.btn1d.setOnClickListener {
            detailViewModel.getChartIntervalData(assetName.toString(), 1)
            binding.priceChange = changeRate.change1d

        }
        binding.btn7d.setOnClickListener {
            detailViewModel.getChartIntervalData(assetName.toString(), 7)
            binding.priceChange = changeRate.change7d
        }

        binding.btn1m.setOnClickListener {
            binding.priceChange = changeRate.change1m
            detailViewModel.getChartIntervalData(assetName.toString(), 30)
        }

        binding.btn1y.setOnClickListener {
            detailViewModel.getChartIntervalData(assetName.toString(), 364)
            binding.priceChange = changeRate.change1y
        }

        //observe selected data interval data
        detailViewModel.interval.observe(this) { interval ->
            setData(interval)
        }

        binding.topAppBar.apply {
            //back button
            this.setNavigationOnClickListener {
                startActivity(Intent(this@AssetDetailActivity, MainActivity::class.java))
            }
            title = assetId
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    favoritesViewModel.getAllFavorites().observe(this) {
                        val isFav = it.filter { it.assetName == assetName }.size == 1
                        if (isFav) {
                            favoritesViewModel.deleteFavorite(assetName.toString())
                            val buttonFav = binding.topAppBar.menu.findItem(R.id.favorite)
                            buttonFav.setIcon(R.drawable.sharp_favorite_24)
                        } else {
                            favoritesViewModel.addFavorite(Favorite(assetName = assetName.toString()))
                            val buttonFav = binding.topAppBar.menu.findItem(R.id.favorite)
                            buttonFav.setIcon(R.drawable.baseline_favorite_24_filled)
                        }
                    }
                    true
                }

                else -> {
                    true
                }
            }
        }

        //subscribe to web socket for live price
        lifecycleScope.launch {
            WebSocketHelper().subscribeWebSocket(asset = assetName.toString(),
                listener = object : WebSocketHelper.IWebSocketListener {
                    override fun observeSocket(data: String) {
                        detailViewModel.postLivePrice(data, assetName.toString())
                    }
                })
        }

        //update price constantly
        detailViewModel.livePrice.observe(this) { livePrice ->
            binding.livePrice = livePrice
        }
    }

    private fun setData(interval: Interval) {
        val chart = binding.chartAsset
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        chart.setBackgroundColor(Color.BLACK)
        chart.setBorderColor(Color.BLACK)

        chart.description.apply {
            text = "Hoopoe"
        }

        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        chart.setDrawGridBackground(true)

        val x: XAxis = chart.xAxis
        x.isEnabled = true
        x.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        x.setDrawGridLines(true)

        val y: YAxis = chart.axisLeft
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(true)
        y.axisLineColor = Color.BLACK

        chart.axisRight.isEnabled = true
        chart.legend.isEnabled = false

        //chart.animateXY(1000, 1000)

        chart.invalidate()

        val values = ArrayList<Entry>()
        for (i in 0 until interval.data.size) {

            //put time and price
            values.add(Entry(i.toFloat(), interval.data[i].priceUsd!!.toFloat()))
        }
        val set1: LineDataSet
        if (chart.data != null && chart.data.getDataSetCount() > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "DataSet")
            set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            set1.setCubicIntensity(0.2f)
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.setLineWidth(1.8f)
            set1.circleRadius = 4f
            set1.setCircleColor(Color.BLACK)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setColor(Color.BLACK)
            set1.setFillColor(Color.BLACK)
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }

            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            chart.setData(data)
        }
    }
}