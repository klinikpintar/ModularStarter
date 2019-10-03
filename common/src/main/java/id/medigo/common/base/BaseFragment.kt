package id.medigo.common.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import id.medigo.common.extension.showSnackbar
import id.medigo.common.extension.showWarningDialog
import id.medigo.common.utils.ErrorHandler
import id.medigo.common.R
import id.medigo.navigation.NavigationCommand

abstract class BaseFragment: Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBackListener()
        setupObserver(getViewModel())
    }

    abstract fun getViewModel(): BaseViewModel

    private fun setupBackListener(){
        if (findNavController().currentDestination?.id != R.id.homeFragment) {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() { onBackEvent() }
                })
        }
    }

    open fun onBackEvent() {
        findNavController().navigateUp()
    }

    fun Toolbar.init(type: ToolbarType = ToolbarType.UP) {
        this.setupWithNavController(findNavController())
        this.setNavigationOnClickListener {
            onBackEvent()
        }
        when (type) {
            ToolbarType.UP ->
                this.setNavigationIcon(R.drawable.ic_arrow_left)
            ToolbarType.CANCEL ->
                this.setNavigationIcon(R.drawable.ic_cancel)
        }
    }

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigateTo to its destination
     */
    open fun setupObserver(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(
                        command.directions,
                        getExtras()
                    )
                    is NavigationCommand.Back -> findNavController().navigateUp()
                    is NavigationCommand.ClearAll -> findNavController().popBackStack(
                        R.id.homeFragment,
                        false
                    )
                    is NavigationCommand.StartActivityForResult -> {
                        val intent = Intent(requireActivity(), command.activity::class.java)
                            .putExtras(command.bundle)
                        startActivityForResult(
                            intent,
                            command.requestCode
                        )
                    }
                }
            }
        })
        viewModel.errorHandler.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                if (it.errorResponse?.code == 401) {
                    viewModel.forceLogout()
                }
                when (it.errorType) {
                    ErrorHandler.ErrorType.SNACKBAR -> showSnackbar(
                        it.errorResponse?.message ?: "Terjadi kesalahan pada sistem",
                        Snackbar.LENGTH_LONG
                    )
                    ErrorHandler.ErrorType.DIALOG -> showWarningDialog(
                        it.errorResponse?.title?: "",
                        it.errorResponse?.message?: ""
                    )
                }
            }
        })
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    enum class ToolbarType {
        UP,
        CANCEL
    }
}