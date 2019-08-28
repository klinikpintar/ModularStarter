package com.medigo.remote.di

import com.medigo.remote.ApiService
import com.medigo.remote.BuildConfig
import com.medigo.remote.BuildConfig.API_VERSION
import com.medigo.remote.BuildConfig.CLIENT_ID
import com.medigo.remote.DataStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
const val HEADER_INTERCEPTOR = "HEADER_INTERCEPTOR"

var remoteModule = module {

    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    // Separated header interceptor
    single(named(HEADER_INTERCEPTOR)) {
        Interceptor { chain ->
            val lOriginalRequest = chain.request()
            val lRequest = lOriginalRequest.newBuilder()
                .header("Authorization", String.format("Bearer ","YOUR_TOKEN"))
                .method(lOriginalRequest.method(), lOriginalRequest.body()).build()

            chain.proceed(lRequest)
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get(named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get(named(HEADER_INTERCEPTOR)))
            .build() }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory{ get<Retrofit>().create(ApiService::class.java) }

    factory { DataStore(get()) }
}