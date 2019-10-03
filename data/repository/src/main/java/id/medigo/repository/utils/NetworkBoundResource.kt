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

abstract class NetworkBoundResource<ResultType, RequestType>(private val gson: Gson) {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) { result.value =
            Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    when (e) {
                        is NetworkErrorException, is UnknownHostException -> {
                            setValue(
                                Resource.error(
                                ErrorResponse(message = "Tidak ada koneksi internet"), loadFromDb()))
                        }
                        else -> setValue(
                            Resource.error(
                            ErrorResponse(message = if (BuildConfig.DEBUG) e.localizedMessage else "Maaf, terjadi kesalahan pada server"), loadFromDb()))
                    }
                }
            } else {
                // Return data from local database
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    // ---

    private suspend fun fetchFromNetwork(dbResult: ResultType?) {
        setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
        createCallAsync().apply {
            if (isSuccessful) {
                body()?.let { saveCallResults(processResponse(it)) }
                setValue(Resource.success(loadFromDb()))
            } else {
                setValue(Resource.error(this.getErrorResponse(gson), loadFromDb()))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(data: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType?

    @MainThread
    protected abstract suspend fun createCallAsync(): Response<RequestType>
}
