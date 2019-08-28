package com.medigo.repository

import androidx.lifecycle.LiveData
import com.medigo.local.dao.ProfileDao
import com.medigo.model.Profile
import com.medigo.remote.DataStore
import com.medigo.repository.utils.NetworkBoundResource
import com.medigo.repository.utils.NetworkResource
import com.medigo.repository.utils.Resource

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