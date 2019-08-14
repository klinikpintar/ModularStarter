package com.medigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProfileAddress (

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("detail")
    var detail: String?,

    @SerializedName("provinceId")
    var provinceId: String?,

    @SerializedName("provinceLabel")
    var provinceLabel: String?,

    @SerializedName("cityId")
    var cityId: String?,

    @SerializedName("cityLabel")
    var cityLabel: String?,

    @SerializedName("districtId")
    var districtId: String?,

    @SerializedName("districtLabel")
    var districtLabel: String?,

    @SerializedName("village")
    var village: String?

)