package id.medigo.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import id.medigo.auth.databinding.FragmentRegisterPasswordBinding
import id.medigo.auth.viewmodel.RegisterViewModel
import id.medigo.common.base.BaseFragment
import id.medigo.common.base.BaseViewModel
import id.medigo.repository.utils.Resource
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterPasswordFragment: BaseFragment(){

    private val registerViewModel: RegisterViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterPasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
        binding.viewmodel = registerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbar.init()
        return binding.root
    }

    override fun getViewModel(): BaseViewModel = registerViewModel

    override fun setupObserver(viewModel: BaseViewModel) {
        super.setupObserver(viewModel)
        binding.viewmodel?.profile?.observe(viewLifecycleOwner, Observer {
            if (it?.status == Resource.Status.LOADING) {
                binding.loadingLayout.showProgressDialog()
            } else {
                binding.loadingLayout.hideProgressDialog()
            }
        })
    }
}