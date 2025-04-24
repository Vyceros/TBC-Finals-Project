package ge.fitness.auth.presentation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute


fun NavController.navigateToLogin() = navigate(LoginRoute)

fun NavGraphBuilder.loginScreen(
    onNavigateHome : () -> Unit,
    onNavigateRegister : () -> Unit,
){
    composable<LoginRoute> {
        LoginScreenRoot(
            onNavigateHome = onNavigateHome,
            onNavigateRegister = onNavigateRegister
        )
    }
}