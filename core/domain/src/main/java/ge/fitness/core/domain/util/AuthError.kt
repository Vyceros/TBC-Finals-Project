package ge.fitness.core.domain.util

sealed interface AuthError : Error {
    enum class LoginError : AuthError {
        INVALID_EMAIL,
        USER_DOESNT_EXIST,
        INVALID_PASSWORD,
        UNKNOWN
    }

    enum class RegisterError : AuthError{
        USER_ALREADY_EXISTS,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        UNKNOWN
    }
}