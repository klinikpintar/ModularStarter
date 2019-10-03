package id.medigo.remote.di

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor(var sharedPreferences: SharedPreferences) : Interceptor {
    private val token: String?
        get() = sharedPreferences.getString(TOKEN, "")

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        token?.let {
            request = if (it.isNotEmpty()) {
                val finalToken = "Bearer $token"
                val lOriginalRequest = chain.request()
                val lRequest = lOriginalRequest.newBuilder()
                    .header("Authorization", finalToken)
                    .method(lOriginalRequest.method(), lOriginalRequest.body()).build()
                lRequest
            } else {
                val lOriginalRequest = chain.request()
                val lRequest = lOriginalRequest.newBuilder()
                    .method(lOriginalRequest.method(), lOriginalRequest.body()).build()
                lRequest
            }
        }
        return chain.proceed(request)
    }
}