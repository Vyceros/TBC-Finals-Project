package ge.fitness.auth.domain.usecase

import javax.inject.Inject

interface ValidateFullNameUseCase {
    operator fun invoke(fullName: String): ValidationResult
}

class ValidateFullNameUseCaseImpl @Inject constructor() : ValidateFullNameUseCase {
    override operator fun invoke(fullName: String): ValidationResult {
        return when {
            fullName.isEmpty() -> ValidationResult.FullNameError.EMPTY
            fullName.isBlank() -> ValidationResult.FullNameError.BLANK
            fullName.length < 3 -> ValidationResult.FullNameError.TOO_SHORT
            else -> ValidationResult.Success
        }
    }
}