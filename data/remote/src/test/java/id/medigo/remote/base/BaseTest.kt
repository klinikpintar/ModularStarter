package id.medigo.remote.base

import android.content.SharedPreferences
import id.medigo.remote.di.HEADER_INTERCEPTOR
import id.medigo.remote.di.TOKEN
import id.medigo.remote.di.remoteModule
import id.medigo.remote.di.ServiceInterceptor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import java.io.File

abstract class BaseTest : KoinTest {

    protected lateinit var mockServer: MockWebServer
    private var sharedPreferences: SharedPreferences = mockk(relaxed = true)

    @Before
    open fun setUp() {
        this.configureMockServer()
        this.configureDi()
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
        stopKoin()
    }

    private fun configureDi() {
        startKoin {
            modules(remoteModule(mockServer.url("/").toString()))
        }
        every { sharedPreferences.getString(TOKEN, "") } returns ""
        declare {
            single(named(HEADER_INTERCEPTOR)) {
                ServiceInterceptor(sharedPreferences)
            }
        }
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}