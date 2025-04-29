package ge.fitness.workout.domain.source

import ge.fitness.core.domain.workout.Exercise

interface WorkoutRemoteDataSource {
    suspend fun getExercises(): List<Exercise>
}