package id.medigo.home.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.home.domain.GetProfileUseCase
import id.medigo.home.fragment.HomeFragmentDirections
import id.medigo.model.Profile
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.RxSchedulers
import io.reactivex.Observable

class HomeMainViewModel(
    private val profileUseCase: GetProfileUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val schedulers: RxSchedulers
): BaseViewModel(preferenceRepository, schedulers){

    var isAuthenticated = MutableLiveData<Boolean>()
    val profile = MutableLiveData<Profile>()

    fun loginButonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    fun registerButtonClicked(){
        navigateTo(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
    }

    fun fethcDataState() {
        this.disposable.addAll(
            this.preferenceRepository.getLoggedInUserId()
                .compose(this.maybeTransformer())
                .toObservable().flatMap {
                    if (it.isNotEmpty()) {
                        fetchUserData(it, false)
                    } else {
                        Observable.just(it)
                    }
                }
                .subscribe({
                    when (it){
                        is Profile -> {
                            isAuthenticated.postValue(true)
                            profile.postValue(it)
                        }
                    }
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun fetchUserData(username: String, shouldFetch: Boolean): Observable<Profile> {
        return this.profileUseCase.invoke(username, shouldFetch).result
                .compose(this.observableTransformer())
    }

    fun logOutClicked(){
        this.logout(true) {
            fethcDataState()
            isAuthenticated.value = false
        }
    }

}