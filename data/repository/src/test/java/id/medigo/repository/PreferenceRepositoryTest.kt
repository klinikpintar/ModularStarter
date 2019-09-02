package id.medigo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.medigo.local.dao.PreferenceDao
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class PreferenceRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dao: PreferenceDao = mockk(relaxed = true)
    private lateinit var preferenceRepository: PreferenceRepository
    private val subscriber = mockk<TestObserver<String>>(relaxed = true)

    @Before
    fun setup(){
        preferenceRepository = PreferenceRepositoryImpl(dao)
    }

    /**
     *  Test [getLoggedInUserId()]
      */
    @Test
    fun `when loggedUserId is empty`(){
        every { dao.getLoggedInUserId() } returns Maybe.empty()

        preferenceRepository.getLoggedInUserId().subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onComplete()
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when loggedUserId is not empty`(){
        every { dao.getLoggedInUserId() } returns Maybe.just(anyString())

        preferenceRepository.getLoggedInUserId().subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onSuccess(anyString())
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    /**
     * Test setLoggedInUserId
     */
    @Test
    fun `when setLoggedInUserId success`(){
        every { dao.savePreference(any()) } returns Completable.complete()

        preferenceRepository.setLoggedInUserId(anyString()).subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onComplete()
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when setLoggedInUserId failed`(){
        val e = Exception("Save failed")
        every { dao.savePreference(any()) } returns Completable.error(e)

        preferenceRepository.setLoggedInUserId(anyString()).subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onError(e)
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    /**
     * Test loggedOutUser
     */
    @Test
    fun `when loggedOutUser success`(){
        every { dao.deletePreference() } returns Completable.complete()
        every { dao.deleteProfile() } returns Completable.complete()

        preferenceRepository.loggedOutUser().subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onComplete()
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

    @Test
    fun `when loggedOutUser failed`(){
        val e = Exception("Save failed")
        every { dao.deletePreference() } returns Completable.error(e)
        every { dao.deleteProfile() } returns Completable.complete()

        preferenceRepository.loggedOutUser().subscribe(subscriber)

        verifyOrder {
            subscriber.onSubscribe(any())
            subscriber.onError(e)
        }

        confirmVerified(subscriber)
        subscriber.dispose()
    }

}