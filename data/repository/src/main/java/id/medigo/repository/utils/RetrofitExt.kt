package id.medigo.repository.utils

import com.google.gson.Gson
import id.medigo.model.ErrorModel
import id.medigo.model.ErrorResponse
import id.medigo.model.ErrorValidation
import id.medigo.repository.BuildConfig
import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.getErrorResponse(mGson: Gson): ErrorResponse {
    var title: String? = null
    var message: String
    try {
        if (this.code() == 401) {
            message = "Your session is ended. Please login again."
        } else {
            val errorResponse = JSONObject(this.errorBody()?.string())
            val errorModel = mGson.fromJson(errorResponse.toString(), ErrorModel::class.java)
            if (errorModel.errors?.isNotEmpty() == true) {
                title = errorModel.message ?: ""
                message = getErrorsMessage(errorModel.errors ?: mutableListOf())
            } else if (errorModel.titleMessage != null && errorModel.detailMessage != null) {
                title = errorModel.titleMessage ?: ""
                message = errorModel.detailMessage ?: ""
            } else {
                message = errorModel.message ?: ""
            }
        }
    } catch (e: Exception) {
        message = if (BuildConfig.DEBUG) this.message() else "Maaf, terjadi kesalahan pada server"
    }
    return ErrorResponse(this.code(), title, message)
}

fun getErrorsMessage(dataList: MutableList<ErrorValidation>): String {
    var result = ""
    for (data in dataList) {
        result = if (result.isNotEmpty()) {
            result + ", " + data.message
        } else {
            data.message ?: "-"
        }
    }
    return result
}
