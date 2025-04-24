package ge.fitness.core.domain.util

typealias DefaultError = Error

sealed interface Resource<out D, out E : DefaultError> {
    data class Success<out D>(val data: D) : Resource<D, Nothing>
    data class Error<out E : DefaultError>(val error: E) : Resource<Nothing, E>
    data object Loading : Resource<Nothing, Nothing>
}