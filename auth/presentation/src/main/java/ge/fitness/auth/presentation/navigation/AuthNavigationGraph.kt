package ge.fitness.auth.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.auth.presentation.home.HomeScreen
import ge.fitness.auth.presentation.initial.IntroPager
import ge.fitness.auth.presentation.login.LoginScreenRoot
import ge.fitness.auth.presentation.signup.SignUpScreenRoot

fun NavController.navigateToLogin() {
    navigate(AuthRoutes.Login.toString()) {
        popUpTo(AuthRoutes.Intro.toString()) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateToSignUp() {
    navigate(AuthRoutes.Register.toString()) { popUpTo(AuthRoutes.Register.toString()) { inclusive = true } }
}

fun NavController.navigateToHome() {
    navigate(AuthRoutes.Home.toString()) {
        popUpTo(AuthRoutes.Intro.toString()) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateToLoginAfterLogout() {
    navigate(AuthRoutes.Login.toString()) {
        // Clear the entire back stack when logging out
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.authGraph(
    onNavigateLogin: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateLogout: () -> Unit,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    authManager: AuthManager
) {
    composable(AuthRoutes.Intro.toString()) { IntroPager(onButtonClick = {onNavigateLogin()})}

    composable(AuthRoutes.Login.toString()) {
        LoginScreenRoot(
            onNavigateHome = onNavigateHome,
            onNavigateRegister = onNavigateRegister,
            snackbarHostState = snackbarHostState
        )
    }

    composable(AuthRoutes.Register.toString()) {
        SignUpScreenRoot(
            onNavigateLogin = onNavigateLogin,
            onBackClick = onBackClick,
            snackbarHostState = snackbarHostState,
            onNavigateHome = onNavigateHome
        )
    }

    composable(AuthRoutes.Home.toString()) {
        HomeScreen(onLogout = {
            FirebaseAuth.getInstance().signOut()
            authManager.logout()
            onNavigateLogout()
        })
    }
}
