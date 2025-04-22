package ge.fitness.auth.presentation.login

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ge.fitness.auth.presentation.navigation.Screen
import ge.fitness.auth.presentation.utils.HandleEvents

fun NavGraphBuilder.loginNavGraph(
    onNavigateToHome: () -> Unit,
    onShowSnackbar: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    composable(Screen.Login.route) {
        val viewModel: LoginViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val savedStateHandle = it.savedStateHandle
        val email = remember { savedStateHandle.get<String>("email") }
        val password = remember { savedStateHandle.get<String>("password") }

        LaunchedEffect(email, password) {
            if (email != null) {
                savedStateHandle.remove<String>("email")
            }
            if (password != null) {
                savedStateHandle.remove<String>("password")
            }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(LoginEvent.CheckUserSession)
        }

        HandleEvents(flow = viewModel.events) { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> onNavigateToHome()
                is LoginEvent.ShowSnackbar -> onShowSnackbar(event.message)
                else -> {}
            }
        }

        LoginScreen(
            state = state,
            email = email,
            password = password,
            onLogin = { emailInput, passwordInput, rememberMe ->
                viewModel.onEvent(LoginEvent.LoginUser(emailInput, passwordInput, rememberMe))
            },
            onRegisterClick = onNavigateToRegister,
            onEmailChange = { emailInput ->
                viewModel.onEvent(LoginEvent.OnEmailChanged(emailInput))
            },
            onPasswordChange = { passwordInput ->
                viewModel.onEvent(LoginEvent.OnPasswordChanged(passwordInput))
            }
        )
    }
}