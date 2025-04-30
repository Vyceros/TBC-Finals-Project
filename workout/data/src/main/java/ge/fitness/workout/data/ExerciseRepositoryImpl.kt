package ge.fitness.workout.data

import ge.fitness.core.database.FireStoreDataSource
import ge.fitness.core.database.mapper.toDomain
import ge.fitness.core.database.mapper.toEntity
import ge.fitness.core.domain.util.ApplicationError
import ge.fitness.core.domain.util.NetworkHandler
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.util.toNetworkError
import ge.fitness.core.domain.workout.Exercise
import ge.fitness.core.domain.workout.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val fireStoreDataSource: FireStoreDataSource,
    private val networkHandler: NetworkHandler
) : ExerciseRepository {

    override suspend fun saveExercise(exercise: Exercise): Flow<Resource<Unit, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = { fireStoreDataSource.saveExercise(exercise.toEntity()) }
        )
    }

    override suspend fun saveExercises(exercises: List<Exercise>): Flow<Resource<Unit, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = { fireStoreDataSource.saveExercises(exercises.map { it.toEntity() }) }
        )
    }

    override fun getExerciseById(id: String): Flow<Resource<Exercise?, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getExerciseById(id).map { it?.toDomain() }.firstOrNull()
            }
        )
    }

    override fun getAllExercises(): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getAllExercises().map { entities ->
                    entities.map { it.toDomain() }
                }.first()
            }
        )
    }

    override fun getExercisesByBodyPart(bodyPart: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getExercisesByBodyPart(bodyPart).map { entities ->
                    entities.map { it.toDomain() }
                }.first()
            }
        )
    }

    override fun getExercisesByEquipment(equipment: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getExercisesByEquipment(equipment).map { entities ->
                    entities.map { it.toDomain() }
                }.first()
            }
        )
    }

    override fun getExercisesByTarget(target: String): Flow<Resource<List<Exercise>, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getExercisesByTarget(target).map { entities ->
                    entities.map { it.toDomain() }
                }.first()
            }
        )
    }

    override suspend fun deleteExercise(id: String): Flow<Resource<Unit, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = { fireStoreDataSource.deleteExercise(id) }
        )
    }

    override suspend fun deleteAllExercises(): Flow<Resource<Unit, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = { fireStoreDataSource.deleteAllExercises() }
        )
    }


}
