package id.medigo.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import id.medigo.common.utils.ErrorHandler
import id.medigo.common.utils.Event
import id.medigo.navigation.NavigationCommand
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.utils.AppDispatchers
import kotlinx.coroutines.launch


abstract class BaseViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val dispatchers: AppDispatchers
): ViewModel() {

    // FOR ERROR HANDLER
    protected val _errorHandler = MutableLiveData<Event<ErrorHandler>>()
    val errorHandler: LiveData<Event<ErrorHandler>> get() = _errorHandler

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    fun forceLogout() = viewModelScope.launch(dispatchers.main) {
        preferenceRepository.clear()
        navigate(NavigationCommand.ClearAll)
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigateTo(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    fun navigate(command: NavigationCommand) {
        _navigation.value = Event(command)
    }
}