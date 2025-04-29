package ge.fitness.workout.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface WorkoutRoutes {
    @Serializable
    data object Home : WorkoutRoutes
}