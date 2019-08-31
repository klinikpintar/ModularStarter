package id.medigo.home.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.home.domain.GetProfileUseCase
import id.medigo.home.fragment.HomeFragmentDirections
import id.medigo.model.Profile

class HomeMainViewModel(
    private val profileUseCase: GetProfileUseCase
): BaseViewModel(){

    var isAuthenticated = MutableLiveData<Boolean>()
    val profile = MutableLiveData<Profile>()

    fun loginButonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    fun registerButtonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    fun fethcDataState() {
        this.disposable.add(
            this.preferenceRepository.getLoggedInUserId()
                .compose(this.maybeTransformer())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        fetchUserData(it, false)
                    }
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun fetchUserData(username: String, shouldFetch: Boolean) {
        this.disposable.addAll(
            this.profileUseCase.invoke(username, shouldFetch).result
                .compose(this.observableTransformer())
                .subscribe({
                    isAuthenticated.postValue(true)
                    profile.postValue(it)
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun logOutClicked(){
        this.logout(true) {
            fethcDataState()
            isAuthenticated.value = false
        }
    }

}