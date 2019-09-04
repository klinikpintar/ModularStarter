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

    fun loginClicked(username: String?, password: String?) {
        userData = this.getLoginUseCase.invoke(username?: "", password?: "")
        this.disposable.add(
            this.userData.result
                .compose(this.observableTransformer())
                .flatMapCompletable {
                    saveUserData(it)
                }
                .subscribe({
                    onLogginSuccess()
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    fun onLogginSuccess()
            = navigateTo(LoginFragmentDirections.actionPopOutAuthFeature())

    fun saveUserData(data: Profile): Completable = Completable
        .concatArray(
            this.userData.storeData(data),
            this.preferenceRepository.setLoggedInUserId(data.login))
        .compose(this.completableTransformer)

}