package com.root14.hoopoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.root14.hoopoe.databinding.ActivityMainBinding
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.view.bottomsheet.MainBottomSheet
import com.root14.hoopoe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //It skips mainBottomSheet.dismiss() in the init phase and then closes the bottomSheet when recyclerview change.
    private var initializer: Boolean = false
    
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressIndicator.isIndeterminate = false

        val mainBottomSheet = MainBottomSheet()

        binding.btnTop100.setOnClickListener {
            mainBottomSheet.show(supportFragmentManager, "")
        }
        binding.btnSearch.setOnClickListener {
            mainBottomSheet.show(supportFragmentManager, "")
        }
        //sort price
        binding.twPriceSort.setOnClickListener {
            mainViewModel.sortPrice()
            binding.rwCoinMain.adapter?.notifyDataSetChanged()
        }
        //sort 24h
        binding.tw24h.setOnClickListener {
            mainViewModel.sort24d()
            binding.rwCoinMain.adapter?.notifyDataSetChanged()
        }
        //sort rank
        binding.twRank.setOnClickListener {
            mainViewModel.sortRank()
            binding.rwCoinMain.adapter?.notifyDataSetChanged()
        }


        //initial value
        mainViewModel.updateCoinList("100").observe(this) { assets ->
            assets.let {
                binding.rwCoinMain.adapter = CoinRecycleAdapter(it)
                binding.rwCoinMain.layoutManager = LinearLayoutManager(this)
                binding.rwCoinMain.setHasFixedSize(true)
                if (initializer) {
                    mainBottomSheet.dismiss()
                }
                initializer = true
            }
        }

    }
}