package ge.fitness.auth.domain.usecase

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val errorMessage: String) : ValidationResult()
}