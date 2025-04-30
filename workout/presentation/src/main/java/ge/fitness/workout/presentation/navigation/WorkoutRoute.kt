package ge.fitness.workout.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface WorkoutRoutes {
    @Serializable
    data object Home : WorkoutRoutes

    @Serializable
    data object Settings : WorkoutRoutes

    @Serializable
    data class WorkoutDetails(val name: String, val description: List<String>, val image: String) :
        WorkoutRoutes

}

sealed class BottomNavItem(
    val route: WorkoutRoutes,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val routeName: String?
) {
    data object Home : BottomNavItem(
        route = WorkoutRoutes.Home,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        routeName = WorkoutRoutes.Home::class.qualifiedName
    )

    data object Settings : BottomNavItem(
        route = WorkoutRoutes.Settings,
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        routeName = WorkoutRoutes.Settings::class.qualifiedName
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Settings
)