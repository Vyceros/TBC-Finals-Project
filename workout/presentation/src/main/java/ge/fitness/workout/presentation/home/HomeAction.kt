package ge.fitness.workout.presentation.home

sealed interface HomeAction {
    data object OnWorkoutClick : HomeAction
}