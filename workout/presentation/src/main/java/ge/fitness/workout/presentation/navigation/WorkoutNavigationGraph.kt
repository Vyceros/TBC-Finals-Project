package ge.fitness.workout.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ge.fitness.workout.presentation.home.HomeScreenRoot
import ge.fitness.workout.presentation.settings.SettingsScreenRoot


fun NavController.navigateToHome() {
    navigate(WorkoutRoutes.Home::class.qualifiedName ?: "home") {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavController.navigateToSettings() {
    navigate(WorkoutRoutes.Settings::class.qualifiedName ?: "settings") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.workoutGraph(
    onLogout: () -> Unit,
    snakcbarHostState: SnackbarHostState
) {
    composable(route = WorkoutRoutes.Home::class.qualifiedName ?: "home") {
        HomeScreenRoot()
    }

    composable(route = WorkoutRoutes.Settings::class.qualifiedName ?: "settings") {
        SettingsScreenRoot(
            onLogout = onLogout,

        )
    }
}