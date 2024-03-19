package com.root14.hoopoe.view.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.data.model.SortTop
import com.root14.hoopoe.databinding.RwTop100Binding
import com.root14.hoopoe.view.viewholder.MainBottomSheetViewHolder
import com.root14.hoopoe.viewmodel.MainViewModel

class MainBottomSheetAdapter(
    private val data: List<SortTop>,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<MainBottomSheetViewHolder>() {
    private lateinit var binding: RwTop100Binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBottomSheetViewHolder {
        binding = RwTop100Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainBottomSheetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainBottomSheetViewHolder, position: Int) {
        holder.binding(data[position])
        holder.itemView.setOnClickListener {
            mainViewModel.selectTop(position)
            notifyDataSetChanged()
        }
    }
}