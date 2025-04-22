package ge.fitness.auth.presentation.signup

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ge.fitness.auth.presentation.navigation.Screen
import ge.fitness.auth.presentation.utils.HandleEvents

fun NavGraphBuilder.signupNavGraph(
    onNavigateBack: (String, String) -> Unit,
    onShowSnackbar: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(Screen.SignUp.route) {
        val viewModel: SignUpViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        HandleEvents(flow = viewModel.events) { event ->
            when (event) {
                is SignUpEvent.NavigateBack -> onNavigateBack(event.email, event.password)
                is SignUpEvent.ShowSnackbar -> onShowSnackbar(event.message)
                else -> {}
            }
        }

        SignUpScreen(
            state = state,
            onSignUp = { fullName, email, password ->
                viewModel.onEvent(SignUpEvent.RegisterUser(fullName, email, password))
            },
            onBackClick = onBackClick,
            onLoginClick = {
                viewModel.onEvent(SignUpEvent.NavigateToLogin)
            },
            onFullNameChange = { fullName ->
                viewModel.validateFullName(fullName)
            },
            onEmailChange = { email ->
                viewModel.validateEmail(email)
            },
            onPasswordChange = { password ->
                viewModel.validatePassword(password)
            },
            onConfirmPasswordChange = { confirmPassword ->
                viewModel.validateConfirmPassword(confirmPassword)
            }
        )
    }
}