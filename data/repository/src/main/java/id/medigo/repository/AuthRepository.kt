package id.medigo.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import id.medigo.local.dao.ProfileDao
import id.medigo.model.Profile
import id.medigo.remote.UserDataSource
import id.medigo.remote.di.TOKEN
import id.medigo.repository.utils.NetworkBoundResource
import id.medigo.repository.utils.Resource
import retrofit2.Response

interface AuthRepository {
    suspend fun login(id: String, password: String): LiveData<Resource<Profile>>
    suspend fun register(id: String, password: String): LiveData<Resource<Profile>>
}

class AuthRepositoryImpl(
    private val dataSource: UserDataSource,
    private val dao: ProfileDao,
    private val pref: SharedPreferences,
    private val gson: Gson
): AuthRepository{

    override suspend fun login(id: String, password: String): LiveData<Resource<Profile>> {
        return object : NetworkBoundResource<Profile, Profile>(gson){
            override fun processResponse(response: Profile): Profile =
                response

            override suspend fun saveCallResults(data: Profile) {
                pref.edit().putString(TOKEN, data.token).apply()
                dao.save(data)
            }

            override fun shouldFetch(data: Profile?): Boolean = true

            override suspend fun loadFromDb(): Profile? =
                dao.getUser()

            override suspend fun createCallAsync(): Response<Profile> =
                dataSource.postLogin(id, password)
        }.build().asLiveData()
    }

    override suspend fun register(id: String, password: String): LiveData<Resource<Profile>> {
        return object : NetworkBoundResource<Profile, Profile>(gson){
            override fun processResponse(response: Profile): Profile =
                response

            override suspend fun saveCallResults(data: Profile) {
                pref.edit().putString(TOKEN, data.token).apply()
                dao.save(data)
            }

            override fun shouldFetch(data: Profile?): Boolean = true

            override suspend fun loadFromDb(): Profile? =
                dao.getUser()

            override suspend fun createCallAsync(): Response<Profile> =
                dataSource.postRegistration(id, password)
        }.build().asLiveData()
    }
}