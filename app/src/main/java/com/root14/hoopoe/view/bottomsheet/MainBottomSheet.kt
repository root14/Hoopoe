package com.root14.hoopoe.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.hoopoe.databinding.FragmentMainBottomSheetBinding
import com.root14.hoopoe.view.adapter.IntervalRecycleAdapter


class MainBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMainBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val mainBottomSheetList = arrayListOf("Top 100", "Top 200", "Top 500", "All Coins")
        binding.rwBottomSheetMain.adapter = IntervalRecycleAdapter(mainBottomSheetList)
        binding.rwBottomSheetMain.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rwBottomSheetMain.setHasFixedSize(true)

        return binding.root
    }
}