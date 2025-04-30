package ge.fitness.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkHandler {
    fun <T, E : DefaultError> wrapWithResourceFlow(
        errorMapper: (Throwable) -> E,
        block: suspend () -> T
    ): Flow<Resource<T, E>> = flow<Resource<T, E>> {
        emit(Resource.Loading)
        val result = block()
        emit(Resource.Success(result))
    }.catch { e ->
        emit(Resource.Error(errorMapper(e)))
    }
}

fun Throwable.toNetworkError(): ApplicationError.NetworkError {
    return when (this) {
        is UnknownHostException -> ApplicationError.NetworkError.NO_INTERNET
        is SocketTimeoutException -> ApplicationError.NetworkError.REQUEST_TIMEOUT
        else -> {
            ApplicationError.NetworkError.UNKNOWN
        }
    }
}