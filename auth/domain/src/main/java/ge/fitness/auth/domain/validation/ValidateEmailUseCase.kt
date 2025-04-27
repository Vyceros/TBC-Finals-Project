package ge.fitness.auth.domain.validation


class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return when {
            !email.matches(emailRegex.toRegex()) -> ValidationResult.EmailError.INVALID_EMAIL
            else -> ValidationResult.Success
        }
    }
}

