package ge.fitness.auth.domain.validation

sealed interface ValidationResult{
    data object Success : ValidationResult
    enum class PasswordError : ValidationResult {
        TOO_SHORT,
        EMPTY,
        NO_DIGIT,
        NO_UPPERCASE
    }
    enum class EmailError : ValidationResult {
        INVALID_EMAIL
    }
    enum class PasswordMatchError : ValidationResult {
        EMPTY,
        NO_MATCH
    }
    enum class FullNameError : ValidationResult {
        EMPTY,
        BLANK,
        TOO_SHORT
    }
}
