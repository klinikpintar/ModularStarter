package com.medigo.repository.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

abstract class NetworkResource<Type>{

    private val result = MutableLiveData<Resource<Type>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkResource<Type> {
        withContext(Dispatchers.Main) { result.value =
            Resource.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            try {
                fetchFromNetwork()
            } catch (e: Exception) {
                Log.e("NetworkBoundResource", "An error happened: $e")
                setValue(Resource.error(e, null))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<Type>>

    // ---

    private suspend fun fetchFromNetwork() {
        Log.d(NetworkBoundResource::class.java.name, "Fetch data from network")
        setValue(Resource.loading(null))
        val apiResponse = createCall()
        Log.e(NetworkBoundResource::class.java.name, "Data fetched from network")
        setValue(Resource.success(apiResponse))
    }

    @MainThread
    private fun setValue(newValue: Resource<Type>) {
        Log.d(NetworkBoundResource::class.java.name, "Resource: $newValue")
        if (result.value != newValue) result.postValue(newValue)
    }

    @MainThread
    protected abstract suspend fun createCall(): Type

}