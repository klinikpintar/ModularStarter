package com.medigo.model

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

    @SerializedName("phoneNumber")
    var phoneNumber: String? = "",

    @SerializedName("birthDate")
    var birthDate: String = "",

    @SerializedName("username")
    var username: String? = "",

    @SerializedName("token")
    var token: String? = "",

    @SerializedName("gender")
    var gender: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("religion")
    var religion: String?,

    @SerializedName("nationality")
    var nationality: String?,

    @SerializedName("address")
    var address: ProfileAddress?

)