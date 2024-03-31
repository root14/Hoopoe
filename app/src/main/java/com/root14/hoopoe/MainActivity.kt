package com.root14.hoopoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.root14.hoopoe.databinding.ActivityMainBinding
import com.root14.hoopoe.utils.DataStoreUtil
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.view.adapter.MainPagerAdapter
import com.root14.hoopoe.view.bottomsheet.MainBottomSheet
import com.root14.hoopoe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //It skips mainBottomSheet.dismiss() in the init phase and then closes the bottomSheet when recyclerview change.
    private var initializer: Boolean = false

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainPagerAdapter

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPagerAdapter()
        mainViewModel.bottomSheetInstance = MainBottomSheet()

        //TODO on-loading process
        binding.progressIndicator.isIndeterminate = false

        binding.btnTop100.setOnClickListener {
            mainViewModel.bottomSheetInstance.show(supportFragmentManager, "")
        }
        binding.btnSearch.setOnClickListener {
            mainViewModel.bottomSheetInstance.show(supportFragmentManager, "")
        }
        //sort price
        binding.twPriceSort.setOnClickListener {
            mainViewModel.sortPrice()
        }
        //sort 24h
        binding.tw24h.setOnClickListener {
            mainViewModel.sort24d()
        }
        //sort rank
        binding.twRank.setOnClickListener {
            mainViewModel.sortRank()
        }

        binding.btnWatchList.setOnClickListener {
            binding.viewpager.currentItem = 1
        }

        binding.btnCoins.setOnClickListener {
            binding.viewpager.currentItem = 0
        }

        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.toggleButton.check(R.id.btn_coins)
                        hideSortElements(false)
                    }

                    1 -> {
                        binding.toggleButton.check(R.id.btn_watchList)
                        hideSortElements(true)
                    }
                }
            }
        })
    }

    private fun setupViewPagerAdapter() {
        val fragmentList = arrayListOf(PagerRvFragment(), PagerWatchListFragment())

        val viewPager = binding.viewpager
        adapter = MainPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        viewPager.adapter = adapter
    }

    private fun hideSortElements(hide: Boolean) {
        if (hide) {
            binding.twRank.visibility = View.INVISIBLE
            binding.twPriceSort.visibility = View.INVISIBLE
            binding.tw24h.visibility = View.INVISIBLE
            binding.twMarketVolume.visibility = View.INVISIBLE
        } else {
            binding.twRank.visibility = View.VISIBLE
            binding.twPriceSort.visibility = View.VISIBLE
            binding.tw24h.visibility = View.VISIBLE
            binding.twMarketVolume.visibility = View.VISIBLE
        }


    }
}