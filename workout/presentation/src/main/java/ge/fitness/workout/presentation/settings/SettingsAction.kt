package ge.fitness.workout.presentation.settings

sealed interface SettingsAction {
    data object Logout : SettingsAction
}
