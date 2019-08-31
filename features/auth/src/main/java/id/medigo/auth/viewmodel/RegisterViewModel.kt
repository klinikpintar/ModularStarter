package id.medigo.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.auth.domain.GetRegisterUseCase
import id.medigo.auth.fragment.LoginFragmentDirections
import id.medigo.auth.fragment.RegisterNameFragmentDirections
import id.medigo.auth.fragment.RegisterPasswordFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.navigation.NavigationCommand
import id.medigo.repository.AppDispatchers
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.utils.DataNetResource
import io.reactivex.Completable

class RegisterViewModel(
    private val getRegisterUseCase: GetRegisterUseCase
): BaseViewModel(){

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
                    navigate(NavigationCommand.ClearAll)
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

}