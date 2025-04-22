package ge.fitness.auth.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ge.fitness.auth.presentation.login.loginNavGraph
import ge.fitness.auth.presentation.signup.signupNavGraph
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    MomentumTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = startDestination
            ) {
                loginNavGraph(
                    onNavigateToHome = {
                        // You'll need to implement this when you have a home screen
                        // For now, it can be empty
                    },
                    onShowSnackbar = showSnackbar,
                    onNavigateToRegister = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )

                signupNavGraph(
                    onNavigateBack = { email, password ->
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set("email", email)
                            set("password", password)
                        }
                        navController.popBackStack()
                    },
                    onShowSnackbar = showSnackbar,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}