package ge.fitness.auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthRoutes {
    @Serializable
    data object Login : AuthRoutes

    @Serializable
    data object Register : AuthRoutes

    @Serializable
    data object Intro : AuthRoutes

    @Serializable
    data object Home : AuthRoutes
}