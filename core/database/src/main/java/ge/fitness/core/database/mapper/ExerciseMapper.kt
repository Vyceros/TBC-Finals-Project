package ge.fitness.core.database.mapper

import ge.fitness.core.database.entity.ExerciseEntity
import ge.fitness.core.domain.workout.Exercise

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        bodyPart = bodyPart,
        equipment = equipment,
        target = target,
        secondaryMuscles = secondaryMuscles,
        instructions = instructions,
        gifUrl = gifUrl
    )
}

fun ExerciseEntity.toDomain(): Exercise {
    return Exercise(
        id = id,
        name = name,
        bodyPart = bodyPart,
        equipment = equipment,
        target = target,
        secondaryMuscles = secondaryMuscles,
        instructions = instructions,
        gifUrl = gifUrl
    )
}
fun List<Exercise>.toEntityList(): List<ExerciseEntity> {
    return map { it.toEntity() }
}

fun List<ExerciseEntity>.toDomainList(): List<Exercise> {
    return map { it.toDomain() }
}