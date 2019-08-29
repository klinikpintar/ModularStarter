package id.medigo.local.dao

import androidx.room.Dao
import androidx.room.Query
import id.medigo.model.Profile

@Dao
abstract class ProfileDao: BaseDao<Profile>() {

    @Query("SELECT * FROM Profile LIMIT 1")
    abstract suspend fun getUser(): Profile

    suspend fun save(profile: Profile) {
        insert(profile)
    }

}