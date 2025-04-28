package ge.fitness.momentum.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.auth.presentation.navigation.AuthRoutes
import ge.fitness.auth.presentation.navigation.authGraph
import ge.fitness.auth.presentation.navigation.navigateToHome
import ge.fitness.auth.presentation.navigation.navigateToLogin
import ge.fitness.auth.presentation.navigation.navigateToLoginAfterLogout
import ge.fitness.auth.presentation.navigation.navigateToSignUp
import ge.fitness.core.data.util.ConnectivityManager
import ge.fitness.momentum.R
import ge.fitness.momentum.ui.rememberMomentumAppState

@Composable
fun MomentumNavHost(
    connectivityManager: ConnectivityManager,
    authManager: AuthManager,
    navController: NavHostController,
    startDestinationOverride: String? = null
) {
    val appState = rememberMomentumAppState(
        connectivityManager = connectivityManager,
        navController = navController
    )

    val snackbarHostState = remember { SnackbarHostState() }

    val initialRoute = startDestinationOverride
        ?: if (authManager.isUserLoggedIn()) AuthRoutes.Home.toString()
        else AuthRoutes.Intro.toString()

    val msg = stringResource(R.string.you_are_offline_some_features_may_not_be_available)
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(Modifier.padding(padding)) {
            NavHost(navController, startDestination = initialRoute) {
                authGraph(
                    onNavigateLogin = {navController.navigateToLogin() },
                    onNavigateRegister = { navController.navigateToSignUp() },
                    onNavigateHome = { navController.navigateToHome() },
                    onNavigateLogout   = { navController.navigateToLoginAfterLogout() },
                    onBackClick = { navController.popBackStack() },
                    snackbarHostState = snackbarHostState,
                    authManager = authManager
                )
            }
        }
    }
}
