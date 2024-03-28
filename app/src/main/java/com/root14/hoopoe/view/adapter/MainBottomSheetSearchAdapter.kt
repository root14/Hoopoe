package com.root14.hoopoe.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.root14.hoopoe.AssetDetailActivity
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.data.model.AssetsData
import com.root14.hoopoe.databinding.RwCoinBinding
import com.root14.hoopoe.view.viewholder.MainBottomSheetSearchViewHolder

class MainBottomSheetSearchAdapter(private val assets: Assets) :
    RecyclerView.Adapter<MainBottomSheetSearchViewHolder>() {
    private lateinit var binding: RwCoinBinding
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MainBottomSheetSearchViewHolder {
        binding = RwCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainBottomSheetSearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return assets.data.size
    }

    override fun onBindViewHolder(holder: MainBottomSheetSearchViewHolder, position: Int) {
        val coin = assets.data[position]
        holder.binding(coin)

        holder.itemView.setOnClickListener {
            val intent = Intent(
                holder.itemView.context, AssetDetailActivity()::class.java
            )

            intent.putExtra("assetId", assets.data[position].symbol.toString())
            intent.putExtra("assetName", assets.data[position].id.toString())

            holder.itemView.context.startActivity(intent)
        }
    }

}