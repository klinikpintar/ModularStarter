package id.medigo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.medigo.local.dao.ProfileDao
import id.medigo.model.Profile
import id.medigo.remote.DataStore
import id.medigo.repository.utils.DataCacheResource
import io.mockk.*
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class UserRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dao = mockk<ProfileDao>(relaxed = true)
    private val dataStore = mockk<DataStore>()
    private lateinit var userRepository: UserRepository
    private lateinit var profileDataCache: DataCacheResource<Profile, Profile>

    private var subscriber = mockk<TestObserver<Profile>>(relaxed = true)

    @Before
    fun setup(){
        userRepository = UserRepositoryImpl(dataStore, dao)
    }

    @Test
    fun `when database is empty`() {
        // Condition
        every { dao.getUser() } returns Maybe.empty()

        // Action
        profileDataCache = userRepository.getProfileWithCache(
            anyString(),
            shouldFetch = false,
            shouldSaveOnIO = false
        )
        profileDataCache.result.subscribe(subscriber)

        // Verify
        verifyOrder {
            subscriber.onSubscribe(any()) // Make sure onsubscribe called
            subscriber.onComplete() // Make sure onComplete called
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when database not empty without fetch`() {
        every { dao.getUser() } returns Maybe.just(Profile())

        profileDataCache = userRepository.getProfileWithCache(
            anyString(),
            shouldFetch = false,
            shouldSaveOnIO = false
        )
        profileDataCache.result.subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onNext(Profile())
            subscriber.onComplete()
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when database not empty and fetch`() {
        every { dao.getUser() } returns Maybe.just(Profile())
        every { dataStore.fetchUser(anyString()) } returns Maybe.just(Profile())

        profileDataCache = userRepository.getProfileWithCache(
            anyString(),
            shouldFetch = true,
            shouldSaveOnIO = false
        )
        profileDataCache.result.subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onNext(Profile()) // Same as above @test because ResultType and RequestType are same
            subscriber.onComplete()
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when database not empty, fetch and save on IO`() {
        every { dao.getUser() } returns Maybe.just(Profile())
        every { dataStore.fetchUser(anyString()) } returns Maybe.just(Profile())

        profileDataCache = userRepository.getProfileWithCache(
            anyString(),
            shouldFetch = true,
            shouldSaveOnIO = true
        )
        profileDataCache.result.subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onNext(Profile()) // Same as above @test because ResultType and RequestType are same
            subscriber.onComplete()
        }

        verify(exactly = 1) { profileDataCache.storeData(any()) }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

}