package ge.fitness.auth.domain.usecase

sealed interface ValidationResult{
    data object Success : ValidationResult
    enum class PasswordError : ValidationResult{
        TOO_SHORT,
        EMPTY,
        NO_DIGIT,
        NO_UPPERCASE
    }
    enum class EmailError : ValidationResult{
        INVALID_EMAIL
    }
}
