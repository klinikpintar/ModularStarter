package id.medigo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.medigo.model.Preference
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface PreferenceDao {

    @Query("SELECT loggedUserId FROM Preference LIMIT 1")
    fun getLoggedInUserId(): Maybe<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePreference(data: Preference): Completable

    @Query("DELETE FROM Preference")
    fun deletePreference(): Completable

    @Query("DELETE FROM Profile")
    fun deleteProfile(): Completable

}