package id.medigo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.medigo.model.Repos
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ReposDao {

    @Query("SELECT * FROM Repos")
    fun getRepos(): Maybe<Repos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(repos: List<Repos>): Completable

}