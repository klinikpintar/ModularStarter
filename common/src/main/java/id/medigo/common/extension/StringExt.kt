package id.medigo.common.extension

data class Validator(val isValid: Boolean, val message: String? = "")

enum class ValidatorType {
    USER_NAME,
    PASSWORD,
}

const val error_empty_password = "Harap masukkan password"
const val error_invalid_password = "Password minimal 6 digit"
const val error_invalid_username = "Nama harus terdiri dari 2 sampai 100 karakter"

fun String?.validate(type: ValidatorType): Validator {
    return when (type) {
        ValidatorType.USER_NAME -> {
            if (this?.length !in 2..100) {
                Validator(false, error_invalid_username)
            } else {
                Validator(true)
            }
        }
        ValidatorType.PASSWORD -> {
            if (this.isNullOrEmpty()) {
                Validator(false, error_empty_password)
            } else {
                if (this.length >= 6) {
                    Validator(true)
                } else {
                    Validator(false, error_invalid_password)
                }
            }
        }
    }
}