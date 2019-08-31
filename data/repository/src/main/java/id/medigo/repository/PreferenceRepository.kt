package id.medigo.repository

import id.medigo.local.dao.PreferenceDao
import id.medigo.model.Preference
import io.reactivex.Completable
import io.reactivex.Maybe

interface PreferenceRepository{
    fun getLoggedInUserId(): Maybe<String?>
    fun setLoggedInUserId(id: String)
    fun loggedOutUser(): Completable
}

class PreferenceRepositoryImpl(
    private val dao: PreferenceDao
): PreferenceRepository {

    override fun getLoggedInUserId(): Maybe<String?>
            = dao.getLoggedInUserId()

    override fun setLoggedInUserId(id: String)
            = dao.savePreference(Preference("1", id))

    override fun loggedOutUser(): Completable {
        return Completable.mergeArray(
            dao.deletePreference(),
            dao.deleteProfile())
    }

}