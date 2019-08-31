package id.medigo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.medigo.home.databinding.FragmentHomeSecondBinding
import id.medigo.home.viewmodel.HomeSecondViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeSecondFragment: Fragment() {

    companion object {
        fun newInstance() = HomeSecondFragment()
    }

    private val viewModel: HomeSecondViewModel by viewModel()
    private lateinit var binding: FragmentHomeSecondBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeSecondBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}