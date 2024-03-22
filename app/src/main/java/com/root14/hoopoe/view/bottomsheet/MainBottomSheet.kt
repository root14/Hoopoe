package com.root14.hoopoe.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.hoopoe.databinding.FragmentMainBottomSheetBinding
import com.root14.hoopoe.view.adapter.MainBottomSheetAdapter
import com.root14.hoopoe.view.adapter.MainBottomSheetSearchAdapter
import com.root14.hoopoe.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//manual injection caused by @AndroidEntryPoint not working with BottomSheetDialogFragment
class MainBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMainBottomSheetBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mainViewModel.bottomSheetSortList.value?.let {
            //top100 top200 ... sorting adapter
            binding.rwBottomSheetMain.adapter = MainBottomSheetAdapter(it, mainViewModel)
        }
        binding.rwBottomSheetMain.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rwBottomSheetMain.setHasFixedSize(true)


        binding.rwBottomSheetMainSearch.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rwBottomSheetMainSearch.setHasFixedSize(true)

        //search view cache-like on view
        binding.bottomSheetSearchSearchView.editText.setOnEditorActionListener { v, actionId, event ->
            binding.searchBar.setText(binding.bottomSheetSearchSearchView.text)
            binding.bottomSheetSearchSearchView.hide()
            return@setOnEditorActionListener false
        }

        //searchbar query
        binding.bottomSheetSearchSearchView.editText.addTextChangedListener {
            val data = mainViewModel.query(it.toString())
            lifecycleScope.launch(Dispatchers.Main) {
                binding.rwBottomSheetMainSearch.adapter = MainBottomSheetSearchAdapter(data)
            }
        }

        return binding.root
    }
}

