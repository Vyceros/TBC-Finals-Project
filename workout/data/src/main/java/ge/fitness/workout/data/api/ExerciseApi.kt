package ge.fitness.workout.data.api

import ge.fitness.workout.data.model.ExerciseDto
import retrofit2.http.GET

interface ExerciseApi {
    @GET(EXERCISES)
    suspend fun getExercises(): List<ExerciseDto>

    companion object{
        private const val EXERCISES = "exercises"
    }
}