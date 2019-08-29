package id.medigo.repository

import androidx.lifecycle.LiveData
import id.medigo.model.Profile
import id.medigo.remote.DataStore
import id.medigo.repository.utils.NetworkResource
import id.medigo.repository.utils.Resource

interface AuthRepository {
    suspend fun login(id: String, password: String): LiveData<Resource<Profile>>
    suspend fun register(id: String, password: String): LiveData<Resource<Profile>>
}

class AuthRepositoryImpl(
    private val dataStore: DataStore
): AuthRepository{

    override suspend fun login(id: String, password: String): LiveData<Resource<Profile>> {
        return object : NetworkResource<Profile>() {
            override suspend fun createCall(): Profile
                    = dataStore.postLoginAsync(id, password)
        }.build().asLiveData()
    }

    override suspend fun register(id: String, password: String): LiveData<Resource<Profile>> {
        return object : NetworkResource<Profile>() {
            override suspend fun createCall(): Profile
                    = dataStore.postRegistrationAsync(id, password)
        }.build().asLiveData()
    }
}