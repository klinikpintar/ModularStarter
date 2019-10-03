package id.medigo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.medigo.model.Repos

@Dao
interface ReposDao {

    @Query("SELECT * FROM Repos")
    suspend fun getRepos(): Repos

    @Query("DELETE FROM Repos")
    suspend fun deleteRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(repos: List<Repos>)

}