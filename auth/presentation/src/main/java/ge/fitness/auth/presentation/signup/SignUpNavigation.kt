package ge.fitness.auth.presentation.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SignupRoute


fun NavController.navigateToSignUp() = navigate(SignupRoute)

fun NavGraphBuilder.signUpScreen(
    onNavigateLogin: () -> Unit
) {
    composable<SignupRoute> {
        SignUpScreenRoot(
            onNavigateLogin = {
                onNavigateLogin()
            }
        )
    }
}