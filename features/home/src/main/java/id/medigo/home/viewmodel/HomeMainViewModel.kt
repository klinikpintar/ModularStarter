package id.medigo.home.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.home.domain.GetProfileUseCase
import id.medigo.home.fragment.HomeFragmentDirections
import id.medigo.model.Profile
import id.medigo.repository.AppDispatchers
import id.medigo.repository.PreferenceRepository

class HomeMainViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val dispatchers: AppDispatchers,
    private val profileUseCase: GetProfileUseCase
): BaseViewModel(){

    var isAuthenticated = MutableLiveData<Boolean>()
    val profile = MutableLiveData<Profile>()
    val loading = MutableLiveData<Boolean>()

    fun loginButonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    fun registerButtonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    fun fethcDataState() {
        this.disposable.add(
            this.preferenceRepository.getLoggedInUserId()
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnSubscribe { loading.postValue(true) }
                .doOnError { loading.postValue(false) }
                .subscribe({
                    loading.postValue(false)
                    if (!it.isNullOrEmpty()) {
                        fetchUserData(it, false)
                    }
                },{
                    loading.postValue(false)
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun fetchUserData(username: String, shouldFetch: Boolean) {
        this.disposable.add(
            this.profileUseCase.invoke(username, shouldFetch).result
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnSubscribe { loading.postValue(true) }
                .doOnComplete { loading.postValue(false) }
                .doOnError {  }
                .subscribe({
                    isAuthenticated.postValue(true)
                    profile.postValue(it)
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun logout(){
        this.disposable.add(
            this.preferenceRepository.loggedOutUser()
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnSubscribe { loading.postValue(true) }
                .doOnComplete { loading.postValue(false) }
                .subscribe({
//                    isAuthenticated.postValue(false)
                    fethcDataState()
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

}