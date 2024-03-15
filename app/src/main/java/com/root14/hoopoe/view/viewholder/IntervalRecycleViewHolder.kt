package com.root14.hoopoe.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.databinding.RwPriceChangeBinding

class IntervalRecycleViewHolder(private val binding: RwPriceChangeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun binding(interval: String) {
        binding.interval = interval
    }
}