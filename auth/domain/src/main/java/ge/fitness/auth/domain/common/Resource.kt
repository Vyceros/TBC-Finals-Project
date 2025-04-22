package ge.fitness.auth.domain.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}
