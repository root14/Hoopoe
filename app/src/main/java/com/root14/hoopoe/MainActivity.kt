package com.root14.hoopoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.root14.hoopoe.databinding.ActivityMainBinding
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

        val result = mainViewModel.getAssests().observe(this) {
            val dummy = it
            dummy.let {
                println("asd")
            }
        }
    }
}