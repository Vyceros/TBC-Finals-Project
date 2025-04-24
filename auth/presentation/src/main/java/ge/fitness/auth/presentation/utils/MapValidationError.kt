package ge.fitness.auth.presentation.utils

import ge.fitness.auth.domain.usecase.ValidationResult
import ge.fitness.auth.presentation.R

fun ValidationResult.toStringRes() : Int?{
    return when(this){
        ValidationResult.EmailError.INVALID_EMAIL -> R.string.invalid_email
        ValidationResult.PasswordError.TOO_SHORT -> R.string.short_password
        ValidationResult.PasswordError.EMPTY -> R.string.empty_password
        ValidationResult.PasswordError.NO_DIGIT -> R.string.password_no_digit
        ValidationResult.PasswordError.NO_UPPERCASE -> R.string.password_no_uppercase
        ValidationResult.Success -> {
            null
        }
    }
}