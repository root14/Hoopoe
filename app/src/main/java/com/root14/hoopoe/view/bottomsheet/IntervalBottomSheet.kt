package com.root14.hoopoe.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.hoopoe.databinding.FragmentMainBottomSheetBinding
import com.root14.hoopoe.view.adapter.IntervalRecycleAdapter

class IntervalBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMainBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val intervalList = arrayListOf("1 Hour", "24 Hour", "7 Day", "30 Day")
        binding.rwPriceChange.adapter = IntervalRecycleAdapter(intervalList)
        binding.rwPriceChange.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rwPriceChange.setHasFixedSize(true)

        return binding.root
    }
}