package ge.fitness.auth.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ge.fitness.auth.presentation.initial.IntroPager
import ge.fitness.auth.presentation.login.LoginScreenRoot
import ge.fitness.auth.presentation.signup.SignUpScreenRoot

fun NavController.navigateToLogin() {
    navigate(AuthRoutes.Login) {
        popUpTo(AuthRoutes.Login) {
            inclusive = true
        }
    }
}

fun NavController.navigateToSignUp() {
    navigate(AuthRoutes.Register) {
        popUpTo(AuthRoutes.Register) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.authGraph(
    onNavigateHome: () -> Unit,
    onNavigateLogin: () -> Unit,
    onNavigateRegister: () -> Unit,
    snakcbarHostState: SnackbarHostState,
    onBackClick: () -> Unit
) {
    composable<AuthRoutes.Intro> {
        IntroPager(
            onButtonClick = onNavigateLogin
        )
    }

    composable<AuthRoutes.Login> {
        LoginScreenRoot(
            onNavigateRegister = {
                onNavigateRegister()
            },
            onNavigateHome = {
                onNavigateHome()
            },
            snackbarHostState = snakcbarHostState
        )
    }
    composable<AuthRoutes.Register> {
        SignUpScreenRoot(
            onNavigateLogin = {
                onNavigateLogin()
            },
            onBackClick = {
                onBackClick()
            }
        )
    }
}