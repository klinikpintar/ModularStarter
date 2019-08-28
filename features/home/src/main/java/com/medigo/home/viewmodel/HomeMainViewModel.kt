package com.medigo.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.medigo.auth.fragment.LoginFragmentDirections
import com.medigo.common.base.BaseViewModel
import com.medigo.common.utils.Event
import com.medigo.home.domain.GetProfileUseCase
import com.medigo.home.fragment.HomeFragmentDirections
import com.medigo.model.Profile
import com.medigo.repository.AppDispatchers
import com.medigo.repository.UserRepository
import com.medigo.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeMainViewModel(
    private val dispatchers: AppDispatchers,
    private val profileUseCase: GetProfileUseCase
): BaseViewModel(){

    val isAuthenticated = MutableLiveData<Boolean>()
    private var profileSource: LiveData<Resource<Profile>> = MutableLiveData()
    private val _profile = MediatorLiveData<Profile>()
    val profile: LiveData<Profile> get() = _profile
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun loginButonClicked(){
        navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    fun registerButtonClicked(){
        navigate(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    fun fethcProfileData(shouldFetch: Boolean) = viewModelScope.launch(dispatchers.main) {
        _profile.removeSource(profileSource)
        withContext(dispatchers.io) { profileSource = profileUseCase(shouldFetch) }
        _profile.addSource(profileSource) {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    loading.postValue(false)
                    _profile.postValue(it.data)
                    isAuthenticated.postValue(true)
                }
                Resource.Status.ERROR -> {
                    loading.postValue(false)
                    _snackbarError.value = Event(it.error?.message?:"Error")
                    isAuthenticated.postValue(false)
                }
                Resource.Status.LOADING -> loading.postValue(true)
            }
        }
    }

}