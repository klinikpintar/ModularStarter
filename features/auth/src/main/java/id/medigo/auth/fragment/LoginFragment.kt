package id.medigo.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import id.medigo.auth.databinding.FragmentLoginBinding
import id.medigo.auth.viewmodel.LoginViewModel
import id.medigo.common.base.BaseFragment
import id.medigo.common.base.BaseViewModel
import id.medigo.repository.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toolbar.init(ToolbarType.CANCEL)
        return binding.root
    }

    override fun getViewModel(): BaseViewModel = viewModel

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