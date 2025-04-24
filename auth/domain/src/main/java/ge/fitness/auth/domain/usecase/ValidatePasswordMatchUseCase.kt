package ge.fitness.auth.domain.usecase

import javax.inject.Inject

interface ValidatePasswordMatchUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult
}

class ValidatePasswordMatchUseCaseImpl @Inject constructor() : ValidatePasswordMatchUseCase {
    override operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isEmpty() -> ValidationResult.PasswordMatchError.EMPTY
            password != confirmPassword -> ValidationResult.PasswordMatchError.NO_MATCH
            else -> ValidationResult.Success
        }
    }
}