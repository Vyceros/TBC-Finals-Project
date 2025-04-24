package ge.fitness.auth.domain.usecase

import javax.inject.Inject

interface ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationResult
}

class ValidatePasswordUseCaseImpl @Inject constructor() : ValidatePasswordUseCase {
    override operator fun invoke(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult.PasswordError.EMPTY
            password.length < 6 -> ValidationResult.PasswordError.TOO_SHORT
            !password.any { it.isDigit() } -> ValidationResult.PasswordError.NO_DIGIT
            !password.any { it.isUpperCase() } -> ValidationResult.PasswordError.NO_UPPERCASE
            else -> ValidationResult.Success
        }
    }
}