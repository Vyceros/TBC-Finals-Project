package ge.fitness.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NetworkHandler {
    fun <T, E : DefaultError> wrapWithResourceFlow(
        errorMapper: (Exception) -> E,
        block: suspend () -> T
    ): Flow<Resource<T, E>> = flow<Resource<T, E>> {
        emit(Resource.Loading)
        val result = block()
        emit(Resource.Success(result))
    }.catch { e ->
        if (e is Exception) {
            emit(Resource.Error(errorMapper(e)))
        }
    }
}