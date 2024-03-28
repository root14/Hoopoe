package com.root14.hoopoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.root14.hoopoe.data.model.Assets
import com.root14.hoopoe.databinding.FragmentPagerWatchListBinding
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.viewmodel.DetailViewModel
import com.root14.hoopoe.viewmodel.FavoritesViewModel

class PagerWatchListFragment : Fragment() {
    private lateinit var binding: FragmentPagerWatchListBinding

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerWatchListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getAllFavorites().observe(viewLifecycleOwner) { favorites ->
            val result = arrayListOf<String>()
            favorites.forEach {
                result.add(it.assetName)
            }

            detailViewModel.getAssetById(*result.toTypedArray()).observe(viewLifecycleOwner) {
                binding.rwWatchlist.adapter = CoinRecycleAdapter(Assets(data = it))
                binding.rwWatchlist.layoutManager = LinearLayoutManager(binding.root.context)
                binding.rwWatchlist.hasFixedSize()
            }

        }
    }
}
