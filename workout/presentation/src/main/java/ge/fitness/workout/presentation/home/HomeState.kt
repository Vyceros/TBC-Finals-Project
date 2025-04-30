package ge.fitness.workout.presentation.home

import ge.fitness.workout.presentation.model.ExerciseUiModel

data class HomeState(
    val exercises : List<ExerciseUiModel> = emptyList(),
    val topWorkout : ExerciseUiModel? = null,
    val isLoading : Boolean = false,
    var selectedExercise : ExerciseUiModel? = null
)
