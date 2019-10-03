package id.medigo.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.Observer
import id.medigo.common_test.datasets.Users.FAKE_USER
import id.medigo.local.dao.ProfileDao
import id.medigo.model.ErrorResponse
import id.medigo.model.Profile
import id.medigo.remote.UserDataSource
import id.medigo.repository.base.BaseTest
import id.medigo.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthRepositoryTest : BaseTest() {

    private var dataSource = mockk<UserDataSource>(relaxed = true)
    private var dao = mockk<ProfileDao>(relaxed = true)
    private var profileObserver: Observer<Resource<Profile>> = mockk(relaxed = true)

    private lateinit var authRepository: AuthRepository

    @Before
    fun setup() {
        authRepository = AuthRepositoryImpl(dataSource, dao, pref, gson)
    }

    @Test
    fun `when no internet`() {
        val exception = NetworkErrorException()
        coEvery { dataSource.postLogin("", "") } throws exception
        coEvery { dao.getUser() } returns null

        runBlocking { authRepository.login("", "").observeForever(profileObserver) }

        verifyOrder {
            profileObserver.onChanged(Resource.loading(null))
            profileObserver.onChanged(Resource.error(ErrorResponse(message = "Tidak ada koneksi internet"), null))
        }

        confirmVerified(profileObserver)
    }

    @Test
    fun `when user login success`() {
        coEvery { dataSource.postLogin("", "") } returns Response.success(FAKE_USER)
        coEvery { dao.getUser() } returns null andThen FAKE_USER

        runBlocking {
            authRepository.login("", "").observeForever(profileObserver)
        }

        verifyOrder {
            profileObserver.onChanged(Resource.loading(null))
            profileObserver.onChanged(Resource.success(FAKE_USER))
        }

        confirmVerified(profileObserver)
    }

    @Test
    fun `when user login failed (wrong credential)`() {
        coEvery { dataSource.postLogin("", "") } returns
                Response.error(400, ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                    """
                        {
                            "code": "bad_request",
                            "message": "Kombinasi nomor telepon dan password tidak sesuai"
                        }
                    """.trimIndent()))
        coEvery { dao.getUser() } returns null

        runBlocking { authRepository.login("", "").observeForever(profileObserver) }

        verifyOrder {
            profileObserver.onChanged(Resource.loading(null))
            profileObserver.onChanged(Resource.error(ErrorResponse(400, message = "Kombinasi nomor telepon dan password tidak sesuai"), null))
        }

        confirmVerified(profileObserver)
    }
}
