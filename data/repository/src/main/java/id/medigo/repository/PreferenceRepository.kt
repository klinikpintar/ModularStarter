package id.medigo.repository

import android.content.SharedPreferences
import id.medigo.local.dao.ProfileDao
import kotlinx.coroutines.runBlocking

interface PreferenceRepository {
    suspend fun clear()
}

class PreferenceRepositoryImpl(
    private val pref: SharedPreferences,
    private val profileDao: ProfileDao
) : PreferenceRepository {

    override suspend fun clear() = runBlocking {
        pref.edit().clear().apply()
        profileDao.deleteUser()
    }
}