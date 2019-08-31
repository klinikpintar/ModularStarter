package id.medigo.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import id.medigo.auth.domain.GetRegisterUseCase
import id.medigo.auth.fragment.RegisterNameFragmentDirections
import id.medigo.auth.fragment.RegisterPasswordFragmentDirections
import id.medigo.common.base.BaseViewModel
import id.medigo.common.utils.Event
import id.medigo.repository.AppDispatchers
import id.medigo.repository.PreferenceRepository

class RegisterViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val getRegisterUseCase: GetRegisterUseCase,
    private val dispatchers: AppDispatchers
): BaseViewModel(){

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun registerNameNextClicked()
            = navigateTo(RegisterNameFragmentDirections.actionRegisterNameFragmentToRegisterPasswordFragment())

    fun registerPasswordNextClicked() {
        this.disposable.add(
            this.getRegisterUseCase.invoke(username.value?: "", password.value?: "").result
                .subscribeOn(dispatchers.io)
                .observeOn(dispatchers.main)
                .doOnSubscribe { loading.postValue(true) }
                .doOnError {
                    loading.postValue(false)
                    _snackbarError.postValue(Event(it.message?: "Error"))
                }
                .doOnComplete { loading.postValue(false) }
                .subscribe({
                    navigateTo(RegisterPasswordFragmentDirections.actionPopOutAuthRegisterFeature())
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

}