package id.medigo.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    var code: Int? = null,
    var title: String? = null,
    var message: String? = null
)

data class ErrorModel(
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("title") var titleMessage: String? = null,
    @SerializedName("detail") var detailMessage: String? = null,
    @SerializedName("errors") var errors: MutableList<ErrorValidation>? = null
)

data class ErrorValidation(
    @SerializedName("field") var errorMessage: String? = null,
    @SerializedName("message") var message: String? = null
)
