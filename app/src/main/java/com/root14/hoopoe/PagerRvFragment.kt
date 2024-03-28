package com.root14.hoopoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.root14.hoopoe.databinding.FragmentPagerRvBinding
import com.root14.hoopoe.view.adapter.CoinRecycleAdapter
import com.root14.hoopoe.view.adapter.MainPagerAdapter
import com.root14.hoopoe.viewmodel.MainViewModel
import hilt_aggregated_deps._com_root14_hoopoe_BaseApplication_GeneratedInjector

class PagerRvFragment : Fragment() {
    private lateinit var binding: FragmentPagerRvBinding

    private val mainViewModel: MainViewModel by activityViewModels()
    private var initializer: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerRvBinding.inflate(layoutInflater)

        mainViewModel.updateCoinList("100").observe(viewLifecycleOwner) { assets ->
            assets.let {
                binding.rwCoinMain.adapter = CoinRecycleAdapter(it)
                binding.rwCoinMain.layoutManager = LinearLayoutManager(binding.root.context)
                binding.rwCoinMain.setHasFixedSize(true)
                if (initializer) {
                    mainViewModel.bottomSheetInstance.dismiss()
                }
                initializer = true
            }
        }
        mainViewModel.notifyChanged.observe(viewLifecycleOwner) {
            binding.rwCoinMain.adapter?.notifyDataSetChanged()
        }
        return binding.root
    }
}