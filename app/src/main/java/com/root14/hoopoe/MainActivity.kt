package com.root14.hoopoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.root14.hoopoe.databinding.ActivityMainBinding
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.view.adapter.MainPagerAdapter
import com.root14.hoopoe.view.bottomsheet.MainBottomSheet
import com.root14.hoopoe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //It skips mainBottomSheet.dismiss() in the init phase and then closes the bottomSheet when recyclerview change.
    private var initializer: Boolean = false

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainPagerAdapter

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    private fun setupViewPagerAdapter() {
        val fragmentList = arrayListOf(PagerRvFragment(), PagerWatchListFragment())

        val viewPager = binding.viewpager
        adapter = MainPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        viewPager.adapter = adapter
    }
}