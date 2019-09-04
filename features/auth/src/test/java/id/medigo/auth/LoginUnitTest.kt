package id.medigo.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import id.medigo.auth.domain.GetLoginUseCase
import id.medigo.auth.viewmodel.LoginViewModel
import id.medigo.common.utils.Event
import id.medigo.model.Profile
import id.medigo.repository.PreferenceRepository
import id.medigo.repository.RxSchedulers
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class LoginUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private var schedulers = RxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())
    private var loginUseCase = mockk<GetLoginUseCase>()
    private var preferenceRepository = mockk<PreferenceRepository>()
    private lateinit var viewmodel: LoginViewModel
    private var snackbarObserver = mockk<Observer<Event<String>>>(relaxed = true)
    private var error = Throwable()
    private val dummyName = "xxx"
    private val dummyPassword = "xxx"
    private val dummyProfile = Profile(name = "xxx", login = "xxx")

    @Test
    fun `when user logIn success`(){
        every { loginUseCase(dummyName,dummyPassword).result } returns Observable.just(dummyProfile)

        viewmodel = LoginViewModel(loginUseCase, preferenceRepository, schedulers)
        viewmodel.loginClicked(dummyName,dummyPassword)

        verify { loginUseCase(dummyName,dummyPassword).storeData(dummyProfile) }

        confirmVerified(preferenceRepository)
        confirmVerified(loginUseCase)
    }

    @Test
    fun `when user logIn failed`(){
        every { loginUseCase(dummyName,dummyPassword).result } returns Observable.error(error)

        viewmodel = LoginViewModel(loginUseCase, preferenceRepository, schedulers)
        viewmodel.loginClicked(dummyName,dummyPassword)

        viewmodel.snackBarError.observeForever(snackbarObserver)

        verify { snackbarObserver.onChanged(viewmodel.snackBarError.value) }

        confirmVerified(snackbarObserver)
    }
}