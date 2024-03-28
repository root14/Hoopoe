package com.root14.hoopoe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.root14.hoopoe.data.entity.Favorite
import com.root14.hoopoe.data.model.Interval
import com.root14.hoopoe.databinding.ActivityAssetDetailBinding
import com.root14.hoopoe.viewmodel.DetailViewModel
import com.root14.hoopoe.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AssetDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityAssetDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
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
        detailViewModel.getIntervalData(assetName.toString()).observe(this) { changeRate ->
            binding.changeRate = changeRate
        }

        detailViewModel.getChartIntervalData(assetName.toString(), 30).observe(this) { interval ->
            setData(interval)
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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

        chart.animateXY(2000, 2000)

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