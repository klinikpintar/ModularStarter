package id.medigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preference(

    @PrimaryKey
    var id: String = "1",

    var loggedUserId: String

)