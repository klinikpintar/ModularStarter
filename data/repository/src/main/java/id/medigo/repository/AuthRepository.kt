package id.medigo.repository

import id.medigo.local.dao.ProfileDao
import id.medigo.model.Profile
import id.medigo.remote.DataStore
import id.medigo.repository.utils.DataNetResource
import io.reactivex.Observable

interface AuthRepository {
    fun login(id: String, password: String, shouldSaveOnIO: Boolean = true): DataNetResource<Profile, Profile>
    fun register(id: String, password: String, shouldSaveOnIO: Boolean = true): DataNetResource<Profile, Profile>
}

class AuthRepositoryImpl(
    private val dataStore: DataStore,
    private val profileDao: ProfileDao
): AuthRepository{

    override fun login(id: String, password: String, shouldSaveOnIO: Boolean): DataNetResource<Profile, Profile> {
        return object : DataNetResource<Profile, Profile>(){
            override fun processResponse(response: Profile): Profile
                    = response

            override fun saveCallResults(data: Profile)
                    = profileDao.save(data)

            override fun shouldSaveOnIO(): Boolean
                    = shouldSaveOnIO

            override fun createCall(): Observable<Profile>
                    = dataStore.postLogin(id, password).toObservable()

        }.build()
    }

    override fun register(id: String, password: String, shouldSaveOnIO: Boolean): DataNetResource<Profile, Profile> {
        return object : DataNetResource<Profile,Profile>(){
            override fun processResponse(response: Profile): Profile
                    = response

            override fun saveCallResults(data: Profile)
                    = profileDao.save(data)

            override fun shouldSaveOnIO(): Boolean
                    = shouldSaveOnIO

            override fun createCall(): Observable<Profile>
                    = dataStore.postRegistration(id, password).toObservable()

        }.build()
    }
}