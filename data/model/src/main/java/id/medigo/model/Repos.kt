package id.medigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repos(

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("full_name")
    var full_name: String = "",

    @SerializedName("description")
    var description: String = ""

)