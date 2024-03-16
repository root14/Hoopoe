package com.root14.hoopoe.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.databinding.RwTop100Binding
import com.root14.hoopoe.view.viewholder.MainBottomSheetViewHolder

class MainBottomSheetAdapter(private val topList: List<String>) :
    RecyclerView.Adapter<MainBottomSheetViewHolder>() {
    private lateinit var binding: RwTop100Binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBottomSheetViewHolder {
        binding = RwTop100Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainBottomSheetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return topList.size
    }

    override fun onBindViewHolder(holder: MainBottomSheetViewHolder, position: Int) {
        holder.binding(topList[position])
    }
}