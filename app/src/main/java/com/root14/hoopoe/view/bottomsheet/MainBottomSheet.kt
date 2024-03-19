package com.root14.hoopoe.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.hoopoe.data.model.SortTop
import com.root14.hoopoe.databinding.FragmentMainBottomSheetBinding
import com.root14.hoopoe.view.adapter.MainBottomSheetAdapter
import com.root14.hoopoe.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.lifecycle.HiltViewModel

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
            binding.rwBottomSheetMain.adapter = MainBottomSheetAdapter(it, mainViewModel)
        }

        binding.rwBottomSheetMain.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rwBottomSheetMain.setHasFixedSize(true)


        return binding.root
    }

}