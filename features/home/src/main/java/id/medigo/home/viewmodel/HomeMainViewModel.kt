package id.medigo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.ErrorHandler
import id.medigo.common.utils.Event
import id.medigo.home.domain.GetProfileUseCase
import id.medigo.home.fragment.HomeFragmentDirections
import id.medigo.model.Profile
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.utils.AppDispatchers
import id.medigo.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeMainViewModel(
    private val profileUseCase: GetProfileUseCase,
    preferenceRepository: PreferenceRepository,
    private val dispatchers: AppDispatchers
): BaseViewModel(preferenceRepository, dispatchers){

    private val _profile = MediatorLiveData<Profile?>()
    val profile: LiveData<Profile?> get() = _profile

    private var profileSource: LiveData<Resource<Profile>> = MutableLiveData<Resource<Profile>>()

    fun loginButonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    fun registerButtonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    fun fethcDataState(shouldFetch: Boolean) = viewModelScope.launch(dispatchers.main) {
        _profile.postValue(null)
        _profile.removeSource(profileSource)
        withContext(dispatchers.io) { profileSource = profileUseCase(shouldFetch) }
        _profile.addSource(profileSource) { resource ->
            _profile.postValue(resource.data)
            if (resource.status == Resource.Status.ERROR) {
                _errorHandler.postValue(Event(ErrorHandler(
                    ErrorHandler.ErrorType.SNACKBAR,
                    resource.error
                )))
            }
        }
    }

    fun logOutClicked() = forceLogout()

}