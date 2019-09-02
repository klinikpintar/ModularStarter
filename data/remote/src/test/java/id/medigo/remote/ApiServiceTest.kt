package id.medigo.remote

import id.medigo.model.Profile
import id.medigo.remote.base.BaseTest
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.net.HttpURLConnection

class ApiServiceTest: BaseTest() {

    private val expectedResult = Profile("22127834","alaskariyy"
        ,"Mahdan Al Askariyy","Medigo Indonesia","Mobile Developer at Medigo Indonesia"
        ,"https://avatars2.githubusercontent.com/u/22127834?v=4")

    @Test
    fun `when login success`(){
        mockHttpResponse(mockServer, "profile_detail.json", HttpURLConnection.HTTP_OK)

        userService.login(anyString())
            .test()
            .assertValue(expectedResult)
    }

    @Test
    fun `when login failed`(){
        mockHttpResponse(mockServer, "profile_detail.json", HttpURLConnection.HTTP_FORBIDDEN)

        userService.login(anyString())
            .test()
            .assertValueCount(0)
    }

}