package com.medigo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medigo.common.base.BaseFragment
import com.medigo.common.base.BaseViewModel
import com.medigo.home.databinding.FragmentHomeSecondBinding
import com.medigo.home.databinding.FragmentHomeThirdBinding
import com.medigo.home.viewmodel.HomeSecondViewModel
import com.medigo.home.viewmodel.HomeThirdViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeThirdFragment: Fragment() {

    companion object {
        fun newInstance() = HomeThirdFragment()
    }

    private val viewModel: HomeThirdViewModel by viewModel()
    private lateinit var binding: FragmentHomeThirdBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeThirdBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}