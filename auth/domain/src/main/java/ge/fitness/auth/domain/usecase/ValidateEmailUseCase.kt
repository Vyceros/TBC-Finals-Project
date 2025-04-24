package ge.fitness.auth.domain.usecase

import javax.inject.Inject

interface ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult
}

class ValidateEmailUseCaseImpl @Inject constructor() : ValidateEmailUseCase {
    override operator fun invoke(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return when {
            !email.matches(emailRegex.toRegex()) -> ValidationResult.EmailError.INVALID_EMAIL
            else -> ValidationResult.Success
        }
    }
}

