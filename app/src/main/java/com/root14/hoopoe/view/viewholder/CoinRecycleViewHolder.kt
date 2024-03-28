package com.root14.hoopoe.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.databinding.RwCoinBinding

class CoinRecycleViewHolder(private val binding: RwCoinBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun binding(assetsData: AssetsData) {
        binding.assets = assetsData
    }
}