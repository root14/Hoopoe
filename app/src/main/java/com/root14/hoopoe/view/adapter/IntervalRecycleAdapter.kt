package com.root14.hoopoe.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.databinding.RwPriceChangeBinding
import com.root14.hoopoe.view.viewholder.IntervalRecycleViewHolder

class IntervalRecycleAdapter(private val interval: List<String>) :
    RecyclerView.Adapter<IntervalRecycleViewHolder>() {
    private lateinit var binding: RwPriceChangeBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalRecycleViewHolder {
        binding = RwPriceChangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntervalRecycleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return interval.size
    }

    override fun onBindViewHolder(holder: IntervalRecycleViewHolder, position: Int) {
        holder.binding(interval[position])
    }
}