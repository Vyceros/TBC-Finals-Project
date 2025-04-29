package ge.fitness.workout.data.model

import ge.fitness.core.domain.workout.Exercise

fun ExerciseDto.toExercise(): Exercise {
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