package id.medigo.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.auth.domain.GetRegisterUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.auth.fragment.RegisterNameFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.navigation.NavigationCommand
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.RxSchedulers
import id.medigo.repository.utils.DataNetResource
import io.reactivex.Completable

class RegisterViewModel(
    private val getRegisterUseCase: GetRegisterUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val schedulers: RxSchedulers
): BaseViewModel(preferenceRepository, schedulers){

    private lateinit var userData: DataNetResource<Profile, Profile>

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun registerNameNextClicked()
            = navigateTo(RegisterNameFragmentDirections.actionRegisterNameFragmentToRegisterPasswordFragment())

    fun registerPasswordNextClicked() {
        userData = this.getRegisterUseCase.invoke(username.value?: "", password.value?: "")
        this.disposable.add(
            this.userData.result
                .compose(this.observableTransformer())
                .flatMapCompletable {
                    saveUserData(it)
                }
                .subscribe({
                    navigateTo(LoginFragmentDirections.actionPopOutAuthFeature())
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    private fun saveUserData(data: Profile)
            = Completable
        .concatArray(
            this.userData.storeData(data),
            this.preferenceRepository.setLoggedInUserId(data.login))
        .compose(this.completableTransformer)

}