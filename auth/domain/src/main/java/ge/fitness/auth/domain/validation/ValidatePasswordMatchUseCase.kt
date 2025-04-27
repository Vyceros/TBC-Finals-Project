package ge.fitness.auth.domain.validation


class ValidatePasswordMatchUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isEmpty() -> ValidationResult.PasswordMatchError.EMPTY
            password != confirmPassword -> ValidationResult.PasswordMatchError.NO_MATCH
            else -> ValidationResult.Success
        }
    }
}