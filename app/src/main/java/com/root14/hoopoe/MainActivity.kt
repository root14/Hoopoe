package com.root14.hoopoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.root14.hoopoe.databinding.ActivityMainBinding
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressIndicator.isIndeterminate = false

        val modalBottomSheet = MainBottomSheet()
        binding.btn7d.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, "")
        }

        mainViewModel.updateCoinList().observe(this) { assets ->
            assets.let { _assets ->
                binding.rwCoinMain.adapter = CoinRecycleAdapter(_assets)
                binding.rwCoinMain.layoutManager = LinearLayoutManager(this)
                binding.rwCoinMain.setHasFixedSize(true)
            }
        }
    }
}