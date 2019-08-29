package id.medigo.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.repository.AppDispatchers
import id.medigo.repository.utils.Resource
import id.medigo.repository.utils.Resource.Status.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val getLoginUseCase: GetLoginUseCase,
    private val dispatchers: AppDispatchers
): BaseViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    private var profileSource: LiveData<Resource<Profile>> = MutableLiveData()
    private val _profile = MediatorLiveData<Profile>()
    val profile: LiveData<Profile> get() = _profile

    fun registerClicked() =
            navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFeature())

    fun loginClicked() = viewModelScope.launch(dispatchers.main) {
        _profile.removeSource(profileSource)
        withContext(dispatchers.io) { profileSource = getLoginUseCase(username.value?:"", password.value?:"") }
        _profile.addSource(profileSource) {
            when (it.status){
                SUCCESS -> {
                    loading.postValue(false)
                    navigate(LoginFragmentDirections.actionPopOutAuthFeature())
                }
                ERROR -> {
                    loading.postValue(false)
                    _snackbarError.value = Event(it.error?.message?:"Error")
                }
                LOADING -> loading.postValue(true)
            }
        }
    }

}