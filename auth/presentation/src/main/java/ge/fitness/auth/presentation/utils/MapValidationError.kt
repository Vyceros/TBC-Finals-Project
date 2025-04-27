package ge.fitness.auth.presentation.utils

import ge.fitness.auth.domain.validation.ValidationResult
import ge.fitness.auth.presentation.R

fun ValidationResult.toStringRes() : Int?{
    return when(this){
        ValidationResult.Success -> null
        ValidationResult.EmailError.INVALID_EMAIL -> R.string.invalid_email
        ValidationResult.PasswordError.TOO_SHORT -> R.string.short_password
        ValidationResult.PasswordError.EMPTY -> R.string.empty_password
        ValidationResult.PasswordError.NO_DIGIT -> R.string.password_no_digit
        ValidationResult.PasswordError.NO_UPPERCASE -> R.string.password_no_uppercase
        ValidationResult.PasswordMatchError.EMPTY -> R.string.error_confirm_password_empty
        ValidationResult.PasswordMatchError.NO_MATCH -> R.string.error_passwords_dont_match
        ValidationResult.FullNameError.EMPTY -> R.string.fullname_empty
        ValidationResult.FullNameError.BLANK -> R.string.fullname_blank
        ValidationResult.FullNameError.TOO_SHORT -> R.string.fullname_too_short
        else -> R.string.validation_error
    }
}