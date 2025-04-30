package ge.fitness.core.domain.util

/**
 *[NetworkError] is all the types of errors that a network/http call might throw.
 * [StorageError] is the errors that we might get from writing/reading from internal storage
 */
sealed interface ApplicationError : Error {

    enum class NetworkError : ApplicationError{
        NO_INTERNET,
        REQUEST_TIMEOUT,
        UNKNOWN
    }

    enum class StorageError : ApplicationError{
        OUT_OF_SPACE,
        NO_PERMISSION
    }
}
