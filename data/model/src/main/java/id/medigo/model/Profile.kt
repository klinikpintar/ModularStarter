package id.medigo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Profile(

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("fullName")
    var fullName: String = "",

    @Embedded
    @SerializedName("address")
    var address: ProfileAddress?

)