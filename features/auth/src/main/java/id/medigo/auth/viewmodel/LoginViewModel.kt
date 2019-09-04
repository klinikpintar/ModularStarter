package id.medigo.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.RxSchedulers
import id.medigo.repository.utils.DataNetResource
import io.reactivex.Completable

class LoginViewModel(
    private val getLoginUseCase: GetLoginUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val schedulers: RxSchedulers
): BaseViewModel(preferenceRepository, schedulers) {

    private lateinit var userData: DataNetResource<Profile, Profile>

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun registerClicked() =
            navigateTo(LoginFragmentDirections.actionLoginFragmentToRegisterFeature())

    fun loginClicked() {
        userData = this.getLoginUseCase.invoke(username.value?: "", password.value?: "")
        this.disposable.add(
            this.userData.result
                .compose(this.observableTransformer())
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
                this.preferenceRepository.setLoggedInUserId(data.login))
                .compose(this.completableTransformer)
                .subscribe({
                    navigateTo(LoginFragmentDirections.actionPopOutAuthFeature())
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

}