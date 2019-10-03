package id.medigo.repository.base

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.medigo.common_test.rules.CoroutinesMainDispatcherRule
import id.medigo.remote.Utils.Key
import id.medigo.remote.di.DataTypeAdapterFactory
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    protected var pref = mockk<SharedPreferences>(relaxed = true)
    protected var gson: Gson = GsonBuilder()
        .registerTypeAdapterFactory(DataTypeAdapterFactory())
        .setDateFormat(Key.fmt_date)
        .setLenient()
        .create()
}
