package id.medigo.common.utils

import id.medigo.model.ErrorResponse

data class ErrorHandler(
    val errorType: ErrorType,
    val errorResponse: ErrorResponse?
) {
    enum class ErrorType {
        SNACKBAR,
        DIALOG
    }
}
