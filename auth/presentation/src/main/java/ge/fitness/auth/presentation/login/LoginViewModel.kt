package ge.fitness.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.AuthRepository
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidationResult
import ge.fitness.auth.presentation.utils.toStringRes
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is LoginAction.OnEmailChanged -> {
                validateEmail(action.email)
            }

            is LoginAction.OnPasswordChanged -> {
                validatePassword(action.password)
            }

            is LoginAction.OnRememberMeChanged -> {
                state = state.copy(rememberMe = !state.rememberMe)
            }

            is LoginAction.OnLoginClick -> {
                login(action.email, action.password)
            }

            else -> Unit
        }
    }

    private fun validateEmail(email: String) {
        val isEmailValid = validateEmailUseCase(email)
        state =
            state.copy(
                isPasswordValid = isEmailValid is ValidationResult.EmailError,
                email = email,
                emailError = isEmailValid.toStringRes(),
                isLoginEnabled = isEmailValid is ValidationResult.Success
            )
    }



    private fun validatePassword(password: String) {
        val isPasswordValid = validatePasswordUseCase(password)
        state = state.copy(
            isPasswordValid = isPasswordValid is ValidationResult.PasswordError,
            passwordError = isPasswordValid.toStringRes(),
            password = password,
            isLoginEnabled = isPasswordValid is ValidationResult.Success
        )
    }

    private fun login(email: String, password: String) {
        state = state.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = authRepository.signIn(email, password)) {
                is Resource.Error -> {
                    _events.send(LoginEvent.ShowError(result.error.toStringRes()))
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(isLoading = false)
                    _events.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}