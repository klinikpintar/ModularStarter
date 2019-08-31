package id.medigo.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.repository.AppDispatchers
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.utils.DataCallResource
import io.reactivex.Completable

class LoginViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val getLoginUseCase: GetLoginUseCase,
    private val dispatchers: AppDispatchers
): BaseViewModel() {

    private lateinit var userData: DataCallResource<Profile, Profile>

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun registerClicked() =
            navigateTo(LoginFragmentDirections.actionLoginFragmentToRegisterFeature())

    fun loginClicked() {
        userData = this.getLoginUseCase.invoke(username.value?: "", password.value?: "")
        this.disposable.add(
            this.userData.result
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnSubscribe { loading.postValue(true) }
                .doOnError {
                    loading.postValue(false)
                    _snackbarError.postValue(Event(it.message?: "Error"))
                }
                .doOnComplete { loading.postValue(false) }
                .subscribe({
                    saveUserData(it)
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    private fun saveUserData(data: Profile) {
        this.disposable.add(
            Completable
                .concatArray(
                this.userData.storeData(data),
                Completable.fromAction { this.preferenceRepository.setLoggedInUserId(data.id) })
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnError {
                    loading.postValue(false)
                    _snackbarError.postValue(Event(it.message?: "Error"))
                }
                .doOnComplete { loading.postValue(false) }
                .subscribe({
                    navigateTo(LoginFragmentDirections.actionPopOutAuthFeature())
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

}