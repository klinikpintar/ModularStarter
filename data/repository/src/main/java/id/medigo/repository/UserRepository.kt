package id.medigo.repository

import id.medigo.local.dao.ProfileDao
import id.medigo.model.Profile
import id.medigo.remote.DataStore
import id.medigo.repository.utils.DataResource
import io.reactivex.Observable

interface UserRepository {
    fun getProfileWithCache(
        username: String,
        shouldFetch: Boolean = true,
        shouldSaveOnIO: Boolean = true
    ): DataResource<Profile, Profile>
}

class UserRepositoryImpl(
    private val dataSource: DataStore,
    private val dao: ProfileDao
): UserRepository {

    override fun getProfileWithCache(username: String, shouldFetch: Boolean, shouldSaveOnIO: Boolean): DataResource<Profile, Profile> {
        return object : DataResource<Profile, Profile>(){

            override fun processResponse(response: Profile): Profile
                    = response

            override fun saveCallResults(data: Profile)
                    = dao.save(data)

            override fun shouldFetch(data: Profile?): Boolean
                    = data == null || shouldFetch

            override fun shouldSaveOnIO(): Boolean
                    = shouldSaveOnIO

            override fun loadFromDb(): Observable<Profile>
                    = dao.getUser().toObservable()

            override fun createCall(): Observable<Profile>
                    = dataSource.fetchUser(username).toObservable()

        }.build()
    }

}