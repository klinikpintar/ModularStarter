package com.medigo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProfileAddress (

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "address_id")
    var id: String = "",

    @SerializedName("detail")
    var detail: String?

)