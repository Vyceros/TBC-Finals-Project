package ge.fitness.workout.domain.usecase

import ge.fitness.core.domain.util.ApplicationError
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.util.toNetworkError
import ge.fitness.core.domain.workout.ExerciseRepository
import ge.fitness.workout.domain.source.WorkoutRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SyncWorkoutUseCase(
    private val repository: ExerciseRepository,
    private val remoteDataSource: WorkoutRemoteDataSource
) {
    suspend operator fun invoke(): Flow<Resource<Unit, ApplicationError.NetworkError>> = flow {
        try {
            val exercises = remoteDataSource.getExercises()
            repository.saveExercises(exercises).collect { result ->
                when (result) {
                    is Resource.Success -> emit(Resource.Success(Unit))
                    is Resource.Error -> emit(result)
                    is Resource.Loading -> { emit(Resource.Loading) }
                }
            }
        } catch (e: Throwable) {
            emit(Resource.Error(e.toNetworkError()))
        }
    }
}