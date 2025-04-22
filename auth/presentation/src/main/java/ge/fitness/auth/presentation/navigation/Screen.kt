package ge.fitness.auth.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login_screen")
    data object SignUp : Screen("signup_screen")
}