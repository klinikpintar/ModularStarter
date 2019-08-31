package id.medigo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.medigo.common.base.BaseFragment
import id.medigo.common.base.BaseViewModel
import id.medigo.home.databinding.FragmentHomeMainBinding
import id.medigo.home.viewmodel.HomeMainViewModel
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
        binding.viewmodel?.fethcDataState()
    }

    override fun getViewModel(): BaseViewModel = viewModel

}