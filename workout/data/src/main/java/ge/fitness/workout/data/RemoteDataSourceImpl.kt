package ge.fitness.workout.data
import ge.fitness.core.domain.workout.Exercise
import ge.fitness.workout.data.api.ExerciseApi
import ge.fitness.workout.data.model.toExercise
import ge.fitness.workout.domain.source.WorkoutRemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: ExerciseApi
) : WorkoutRemoteDataSource {
    override suspend fun getExercises(): List<Exercise> {
        val exercises = api.getExercises().map { it.toExercise() }
        return exercises
    }
}