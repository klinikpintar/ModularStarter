package id.medigo.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.medigo.common.base.BaseFragment
import id.medigo.common.base.BaseViewModel
import id.medigo.home.R
import id.medigo.home.databinding.FragmentHomeBinding
import id.medigo.home.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : BaseFragment(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewModel: HomeViewModel by viewModel()

    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    private val fragments = listOf(
        HomeMainFragment.newInstance(),
        HomeSecondFragment.newInstance(),
        HomeThirdFragment.newInstance()
    )

    private lateinit var binding: FragmentHomeBinding

    private val indexToPage = mapOf(0 to R.id.home_main, 1 to R.id.home_second, 2 to R.id.home_third)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // setup main view pager
        binding.nsvpContent.addOnPageChangeListener(this)
        binding.nsvpContent.adapter = ViewPagerAdapter()
        binding.nsvpContent.offscreenPageLimit = fragments.size

        binding.navigation.setOnNavigationItemSelectedListener(this)

        // initialize backStack with elements
        if (backStack.empty()) backStack.push(0)
    }

    /// BottomNavigationView ItemSelected Implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (binding.nsvpContent.currentItem != position) setItem(position)
        return true
    }

    override fun getViewModel(): BaseViewModel = viewModel

    /// OnPageSelected Listener Implementation
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.home
        if (binding.navigation.selectedItemId != itemId) binding.navigation.selectedItemId = itemId
    }

    private fun setItem(position: Int) {
        binding.nsvpContent.currentItem = position
        backStack.push(position)
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(childFragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }

}