package id.medigo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.medigo.model.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM Profile LIMIT 1")
    suspend fun getUser(): Profile?

    @Query("DELETE FROM Profile")
    suspend fun deleteUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profile: Profile)

}