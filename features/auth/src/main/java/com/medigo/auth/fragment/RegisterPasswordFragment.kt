package com.medigo.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.medigo.auth.databinding.FragmentRegisterPasswordBinding
import com.medigo.auth.viewmodel.RegisterViewModel
import com.medigo.common.base.BaseFragment
import com.medigo.common.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterPasswordFragment: BaseFragment(){

    private val registerViewModel: RegisterViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterPasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
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