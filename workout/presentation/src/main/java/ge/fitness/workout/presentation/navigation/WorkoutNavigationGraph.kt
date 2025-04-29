package ge.fitness.workout.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ge.fitness.workout.presentation.home.HomeScreenRoot


fun NavController.navigateToHome() {
    navigate(WorkoutRoutes.Home) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}


fun NavGraphBuilder.workoutGraph(
    onLogout: () -> Unit,
    snakcbarHostState: SnackbarHostState
) {

    composable<WorkoutRoutes.Home> {
        HomeScreenRoot {  }
    }
}