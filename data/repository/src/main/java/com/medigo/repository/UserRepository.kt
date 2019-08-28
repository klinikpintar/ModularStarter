package com.medigo.repository

import androidx.lifecycle.LiveData
import com.medigo.local.dao.ProfileDao
import com.medigo.model.Profile
import com.medigo.remote.DataStore
import com.medigo.repository.utils.NetworkBoundResource
import com.medigo.repository.utils.Resource

interface UserRepository {
    suspend fun getProfileWithCache(shouldFetch: Boolean): LiveData<Resource<Profile>>
    suspend fun saveProfile(inputForm: HashMap<String, Any>): LiveData<Resource<Profile>>
}

class UserRepositoryImpl(
    private val dataSource: DataStore,
    private val dao: ProfileDao
): UserRepository {

    override suspend fun getProfileWithCache(shouldFetch: Boolean): LiveData<Resource<Profile>> {
        return object : NetworkBoundResource<Profile, Profile>() {

            override fun processResponse(response: Profile): Profile
                    = response

            override suspend fun saveCallResults(data: Profile)
                    = dao.save(data)

            override fun shouldFetch(data: Profile?): Boolean
                    = data == null || shouldFetch

            override suspend fun loadFromDb(): Profile
                    = dao.getUser()

            override suspend fun createCall(): Profile
                    = dataSource.fetchUserAsync()

        }.build().asLiveData()
    }

    override suspend fun saveProfile(inputForm: HashMap<String, Any>): LiveData<Resource<Profile>> {
        return object : NetworkBoundResource<Profile, Profile>() {

            override fun processResponse(response: Profile): Profile
                    = response

            override suspend fun saveCallResults(data: Profile)
                    = dao.save(data)

            override fun shouldFetch(data: Profile?): Boolean
                    = true

            override suspend fun loadFromDb(): Profile
                    = dao.getUser()

            override suspend fun createCall(): Profile
                    = dataSource.postUserAsync(inputForm)

        }.build().asLiveData()
    }

}