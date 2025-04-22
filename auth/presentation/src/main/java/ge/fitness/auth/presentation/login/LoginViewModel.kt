package ge.fitness.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.common.Resource
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
    // Add your actual login use case when implemented
    // private val loginUseCase: LoginUseCase
) : ViewModel() {

    // State
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Events
    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    // Login resource state
    private val _loginResource = MutableStateFlow<Resource<Unit>>(Resource.Success(Unit))

    // Handle user intents
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                val result = validateEmailUseCase(event.email)
                _state.value = _state.value.copy(
                    email = event.email,
                    emailError = if (result is ValidationResult.Error) result.errorMessage else null
                )
            }
            is LoginEvent.OnPasswordChanged -> {
                val result = validatePasswordUseCase(event.password)
                _state.value = _state.value.copy(
                    password = event.password,
                    passwordError = if (result is ValidationResult.Error) result.errorMessage else null
                )
            }
            is LoginEvent.OnRememberMeChanged -> {
                _state.value = _state.value.copy(rememberMe = event.rememberMe)
            }
            is LoginEvent.LoginUser -> {
                loginUser(event.email, event.password, event.rememberMe)
            }
            is LoginEvent.CheckUserSession -> {
                checkUserSession()
            }
            else -> {} // Other events are handled at the UI level
        }
    }

    // Rest of validation methods...

    private fun loginUser(email: String, password: String, rememberMe: Boolean) {
        // Validate inputs first
        val emailResult = validateEmailUseCase(email)
        val passwordResult = validatePasswordUseCase(password)

        val hasError = emailResult is ValidationResult.Error || passwordResult is ValidationResult.Error

        if (hasError) {
            _state.value = _state.value.copy(
                emailError = if (emailResult is ValidationResult.Error) emailResult.errorMessage else null,
                passwordError = if (passwordResult is ValidationResult.Error) passwordResult.errorMessage else null
            )
            return
        }

        // Set loading state using Resource
        _loginResource.value = Resource.Loading(true)
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                // Here you would call your actual login use case
                // val result = loginUseCase(email, password, rememberMe)

                // For now, simulate success after a delay
                kotlinx.coroutines.delay(1000)

                // Update resource state to success
                _loginResource.value = Resource.Success(Unit)

                // Emit success event
                _events.emit(LoginEvent.NavigateToHome)

                // Update state
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                // Handle error with Resource
                _loginResource.value = Resource.Error("Login failed: ${e.message}")
                _state.value = _state.value.copy(isLoading = false)
                _events.emit(LoginEvent.ShowSnackbar("Login failed: ${e.message}"))
            }
        }
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            _loginResource.value = Resource.Loading(true)

            try {
                // Here you would check if user is already logged in
                // val isLoggedIn = userSessionRepository.isUserLoggedIn()
                // if (isLoggedIn) {
                //     _loginResource.value = Resource.Success(Unit)
                //     _events.emit(LoginEvent.NavigateToHome)
                // } else {
                //     _loginResource.value = Resource.Success(Unit) // Just to indicate checking completed
                // }

                // For now, this is just a placeholder
                _loginResource.value = Resource.Success(Unit)
            } catch (e: Exception) {
                _loginResource.value = Resource.Error("Session check failed: ${e.message}")
                // You might want to handle this error silently or show a message
            }
        }
    }
}