package id.medigo.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import id.medigo.common.utils.Event
import id.medigo.navigation.NavigationCommand

abstract class BaseViewModel: ViewModel() {

    // FOR ERROR HANDLER
    protected val _snackbarError = MutableLiveData<Event<String>>()
    val snackBarError: LiveData<Event<String>> get() = _snackbarError

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }
}