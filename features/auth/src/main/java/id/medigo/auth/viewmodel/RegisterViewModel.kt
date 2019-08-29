package id.medigo.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.domain.GetRegisterUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.auth.fragment.RegisterNameFragmentDirections
import id.medigo.auth.fragment.RegisterPasswordFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.repository.AppDispatchers
import id.medigo.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val getRegisterUseCase: GetRegisterUseCase,
    private val dispatchers: AppDispatchers
): BaseViewModel(){

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    private var profileSource: LiveData<Resource<Profile>> = MutableLiveData()
    private val _profile = MediatorLiveData<Profile>()
    val profile: LiveData<Profile> get() = _profile

    fun registerNameNextClicked()
            = navigate(RegisterNameFragmentDirections.actionRegisterNameFragmentToRegisterPasswordFragment())

    fun registerPasswordNextClicked() = viewModelScope.launch(dispatchers.main) {
        _profile.removeSource(profileSource)
        withContext(dispatchers.io) { profileSource = getRegisterUseCase(username.value?:"", password.value?:"") }
        _profile.addSource(profileSource) {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    loading.postValue(false)
                    navigate(RegisterPasswordFragmentDirections.actionPopOutAuthRegisterFeature())
                }
                Resource.Status.ERROR -> {
                    loading.postValue(false)
                    _snackbarError.value = Event(it.error?.message?:"Error")
                }
                Resource.Status.LOADING -> loading.postValue(true)
            }
        }
    }

}