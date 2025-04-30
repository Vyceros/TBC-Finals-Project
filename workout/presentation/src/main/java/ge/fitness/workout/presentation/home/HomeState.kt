package ge.fitness.workout.presentation.home

import ge.fitness.workout.presentation.model.ArticleUiModel
import ge.fitness.workout.presentation.model.ExerciseUiModel

data class HomeState(
    val exercises : List<ExerciseUiModel> = emptyList(),
    val topWorkout : ExerciseUiModel? = null,
    val articles : List<ArticleUiModel> = emptyList(),
    val isLoading : Boolean = false,
)
