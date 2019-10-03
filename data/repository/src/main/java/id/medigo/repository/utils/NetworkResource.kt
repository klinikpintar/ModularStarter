package id.medigo.repository.utils

import android.accounts.NetworkErrorException
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import id.medigo.model.ErrorResponse
import id.medigo.repository.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

abstract class NetworkResource<ResultType, RequestType>(private val gson: Gson) {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) { result.value =
            Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            try {
                fetchFromNetwork()
            } catch (e: Exception) {
                when (e) {
                    is NetworkErrorException, is UnknownHostException -> {
                        setValue(
                            Resource.error(
                            ErrorResponse(message = "Tidak ada koneksi internet"), null))
                    }
                    else -> setValue(
                        Resource.error(
                        ErrorResponse(message = if (BuildConfig.DEBUG) e.localizedMessage else "Maaf, terjadi kesalahan pada server"), null))
                }
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    // ---

    private suspend fun fetchFromNetwork() {
        createCallAsync().apply {
            if (isSuccessful) {
                setValue(Resource.success(body()?.let { processResponse(it) }))
            } else {
                setValue(Resource.error(this.getErrorResponse(gson), null))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @MainThread
    protected abstract suspend fun createCallAsync(): Response<RequestType>
}
