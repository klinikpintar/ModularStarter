package id.medigo.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import id.medigo.auth.databinding.FragmentRegisterNameBinding
import id.medigo.auth.viewmodel.RegisterViewModel
import id.medigo.common.base.BaseFragment
import id.medigo.common.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterNameFragment: BaseFragment(){

    private val registerViewModel: RegisterViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterNameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterNameBinding.inflate(inflater, container, false)
        binding.viewmodel = registerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbar.init()
        return binding.root
    }

    override fun getViewModel(): BaseViewModel = registerViewModel

}