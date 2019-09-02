package id.medigo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.medigo.local.dao.ProfileDao
import id.medigo.model.Profile
import id.medigo.remote.DataStore
import id.medigo.repository.utils.DataNetResource
import io.mockk.*
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class AuthRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dao = mockk<ProfileDao>(relaxed = true)
    private val dataStore = mockk<DataStore>()
    private lateinit var authRepository: AuthRepository
    private lateinit var authDataCache: DataNetResource<Profile, Profile>

    private var subscriber = mockk<TestObserver<Profile>>(relaxed = true)

    @Before
    fun setup(){
        authRepository = AuthRepositoryImpl(dataStore, dao)
    }

    @Test
    fun `when user login and shouldSaveOnIO`() {
        every { dataStore.postLogin(any(),any()) } returns Maybe.just(Profile())

        authDataCache = authRepository.login(anyString(), anyString(), true)
        authDataCache.result.subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onNext(Profile()) // ResultType and RequestType are same
            subscriber.onComplete()
        }

        verify(exactly = 1) { authDataCache.storeData(any()) }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when user login and not shouldSaveOnIO`() {
        every { dataStore.postLogin(any(),any()) } returns Maybe.just(Profile())

        authDataCache = authRepository.login(anyString(), anyString(), false)
        authDataCache.result.subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onNext(Profile()) // ResultType and RequestType are same
            subscriber.onComplete()
        }

        verify(exactly = 0) { authDataCache.storeData(any()) }

        confirmVerified(subscriber)
        subscriber.dispose()
    }
}