package id.medigo.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import id.medigo.common.utils.Event
import id.medigo.navigation.NavigationCommand
import id.medigo.repository.RxSchedulers
import id.medigo.repository.PreferenceRepository
import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class BaseViewModel: ViewModel(), KoinComponent {

    val preferenceRepository: PreferenceRepository by inject()
    private val schedulers: RxSchedulers by inject()

    fun <T> maybeTransformer(): MaybeTransformer<T, T> = MaybeTransformer { maybe ->
        maybe.subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doOnSubscribe { _loading.value = true }
            .doOnComplete { _loading.value = false }
            .doOnError { _snackbarError.value = Event(it.message?: "Something bad happen") }
    }

    fun <T> observableTransformer(): ObservableTransformer<T, T> = ObservableTransformer { maybe ->
        maybe.subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doOnSubscribe { _loading.value = true }
            .doOnComplete { _loading.value = false }
            .doOnError { _snackbarError.value = Event(it.message?: "Something bad happen") }
    }

    val completableTransformer = CompletableTransformer { completable ->
        completable.subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doOnSubscribe { _loading.value = true }
            .doOnComplete { _loading.value = false }
            .doOnError { _snackbarError.value = Event(it.message?: "Something bad happen") }
    }

    // FOR LOADING HANDLER
    protected val _loading =  MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // FOR ERROR HANDLER
    protected val _snackbarError = MutableLiveData<Event<String>>()
    val snackBarError: LiveData<Event<String>> get() = _snackbarError

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    protected var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        this.disposable.dispose()
        super.onCleared()
    }

    fun logout(isFromHome: Boolean = false, func: (() -> Unit)? = null){
        this.disposable.add(
            this.preferenceRepository.loggedOutUser()
                .compose(completableTransformer)
                .subscribe({
                    func?.invoke()
                    if (!isFromHome) navigate(NavigationCommand.ClearAll)
                },{
                    _snackbarError.postValue(Event(it.message?: "Error"))
                })
        )
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigateTo(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    fun navigate(command: NavigationCommand) {
        _navigation.value = Event(command)
    }
}