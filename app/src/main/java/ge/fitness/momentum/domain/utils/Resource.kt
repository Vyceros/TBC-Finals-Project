package ge.fitness.momentum.domain.utils


private typealias RootError = Error
//Generic wrapper class to handle the errors the app might throw.

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, Nothing>
    data class Error<out D, out E : RootError>(val error: E) : Resource<Nothing, E>
    data object Loading : Resource<Nothing, Nothing>
}