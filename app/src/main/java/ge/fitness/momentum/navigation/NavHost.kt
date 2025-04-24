package ge.fitness.momentum.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ge.fitness.auth.presentation.login.LoginRoute
import ge.fitness.auth.presentation.login.loginScreen
import ge.fitness.auth.presentation.login.navigateToLogin
import ge.fitness.auth.presentation.signup.navigateToSignUp
import ge.fitness.auth.presentation.signup.signUpScreen

@Composable
fun MomentumNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        loginScreen(
            onNavigateRegister = navController::navigateToSignUp,
            onNavigateHome = navController::navigateToLogin
        )
        signUpScreen(
            onNavigateLogin = navController::navigateToLogin
        )
    }
}