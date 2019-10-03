package id.medigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Profile(

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("login")
    var login: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("company")
    var company: String = "",

    @SerializedName("bio")
    var bio: String = "",

    @SerializedName("avatar_url")
    var avatar_url: String = "",

    @SerializedName("token")
    var token: String = ""

)