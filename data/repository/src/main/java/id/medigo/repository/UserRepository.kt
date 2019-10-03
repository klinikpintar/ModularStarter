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

interface UserRepository {
    suspend fun getProfileWithCache(
        shouldFetch: Boolean = true
    ): LiveData<Resource<Profile>>
}

class UserRepositoryImpl(
    private val dataSource: UserDataSource,
    private val dao: ProfileDao,
    private val pref: SharedPreferences,
    private val gson: Gson
): UserRepository {
    override suspend fun getProfileWithCache(
        shouldFetch: Boolean
    ): LiveData<Resource<Profile>> {
        return object : NetworkBoundResource<Profile, Profile>(gson){
            override fun processResponse(response: Profile): Profile =
                response

            override suspend fun saveCallResults(data: Profile) {
                pref.edit().putString(TOKEN, data.token).apply()
                dao.save(data)
            }

            override fun shouldFetch(data: Profile?): Boolean
                    = data == null || shouldFetch

            override suspend fun loadFromDb(): Profile? =
                dao.getUser()

            override suspend fun createCallAsync(): Response<Profile> =
                dataSource.fetchUser()
        }.build().asLiveData()
    }


}