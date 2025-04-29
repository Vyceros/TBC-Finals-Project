package ge.fitness.core.domain.workout

import ge.fitness.core.domain.util.ApplicationError
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun saveExercise(exercise: Exercise): Flow<Resource<Unit, ApplicationError.NetworkError>>
    suspend fun saveExercises(exercises: List<Exercise>): Flow<Resource<Unit, ApplicationError.NetworkError>>
    fun getExerciseById(id: String): Flow<Resource<Exercise?, ApplicationError.NetworkError>>
    fun getAllExercises(): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>>
    fun getExercisesByBodyPart(bodyPart: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>>
    fun getExercisesByEquipment(equipment: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>>
    fun getExercisesByTarget(target: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>>
    suspend fun deleteExercise(id: String): Flow<Resource<Unit, ApplicationError.NetworkError>>
    suspend fun deleteAllExercises(): Flow<Resource<Unit, ApplicationError.NetworkError>>
}