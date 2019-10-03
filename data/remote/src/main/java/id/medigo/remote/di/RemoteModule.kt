package id.medigo.remote.di

import com.google.gson.GsonBuilder
import id.medigo.remote.UserDataSource
import id.medigo.remote.Utils.Constants.TIMEOUT
import id.medigo.remote.Utils.Key
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import id.medigo.remote.api.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
const val HEADER_INTERCEPTOR = "HEADER_INTERCEPTOR"
const val TOKEN = "TOKEN"

fun remoteModule(baseUrl: String) = module {

    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply { level = HttpLoggingInterceptor.Level.HEADERS }
    }

    single(named(HEADER_INTERCEPTOR)) {
        ServiceInterceptor(get())
    }

    single {
        GsonBuilder()
            .registerTypeAdapterFactory(DataTypeAdapterFactory())
            .setDateFormat(Key.fmt_date)
            .setLenient()
            .create()
    }

    factory {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get(named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get(named(HEADER_INTERCEPTOR)))
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory{ get<Retrofit>().create(UserService::class.java) }

    factory { UserDataSource(get()) }
}