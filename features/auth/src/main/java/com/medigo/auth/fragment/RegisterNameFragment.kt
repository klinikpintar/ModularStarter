package com.medigo.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.medigo.auth.databinding.FragmentRegisterNameBinding
import com.medigo.auth.viewmodel.RegisterViewModel
import com.medigo.common.base.BaseFragment
import com.medigo.common.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterNameFragment: BaseFragment(){

    private val registerViewModel: RegisterViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterNameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterNameBinding.inflate(inflater, container, false)
        binding.viewmodel = registerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbar.setupWithNavController(findNavController())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel?.profile?.observe(viewLifecycleOwner, Observer {  })
    }

    override fun getViewModel(): BaseViewModel = registerViewModel

}