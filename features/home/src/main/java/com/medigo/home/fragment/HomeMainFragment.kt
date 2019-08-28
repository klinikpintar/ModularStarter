package com.medigo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.medigo.common.base.BaseFragment
import com.medigo.common.base.BaseViewModel
import com.medigo.home.databinding.FragmentHomeMainBinding
import com.medigo.home.viewmodel.HomeMainViewModel
import com.medigo.home.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeMainFragment: BaseFragment() {

    companion object {
        fun newInstance() = HomeMainFragment()
    }

    private val viewModel: HomeMainViewModel by viewModel()
    private lateinit var binding: FragmentHomeMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeMainBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel?.fethcProfileData(false)
    }

    override fun getViewModel(): BaseViewModel = viewModel

}