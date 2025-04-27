package ge.fitness.auth.domain.validation



class ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult.PasswordError.EMPTY
            password.length < 6 -> ValidationResult.PasswordError.TOO_SHORT
            !password.any { it.isDigit() } -> ValidationResult.PasswordError.NO_DIGIT
            !password.any { it.isUpperCase() } -> ValidationResult.PasswordError.NO_UPPERCASE
            else -> ValidationResult.Success
        }
    }
}