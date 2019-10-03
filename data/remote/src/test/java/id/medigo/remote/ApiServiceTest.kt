package id.medigo.remote

import id.medigo.common_test.datasets.Users.FAKE_USER
import id.medigo.model.Profile
import id.medigo.remote.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection

class ApiServiceTest: BaseTest() {

    private val userDataSource by inject<UserDataSource>()

    @Test
    fun `when login success`() {
        mockHttpResponse(mockServer, "profile_detail.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val profile = userDataSource.postLogin("", "").body()
            compareUser(FAKE_USER, profile)
        }
    }

    @Test
    fun `when login failed`() {
        mockHttpResponse(mockServer, "profile_detail.json", HttpURLConnection.HTTP_FORBIDDEN)

        runBlocking {
            val profileResponse = userDataSource.postLogin("", "")
            Assert.assertFalse(profileResponse.isSuccessful)
            Assert.assertNull(profileResponse.body())
        }
    }

    private fun compareUser(expected: Profile, actual: Profile?) {
        Assert.assertEquals(expected.id, actual?.id)
        Assert.assertEquals(expected.login, actual?.login)
        Assert.assertEquals(expected.name, actual?.name)
        Assert.assertEquals(expected.company, actual?.company)
    }

}