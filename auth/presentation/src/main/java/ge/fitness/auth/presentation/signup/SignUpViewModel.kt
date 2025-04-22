package ge.fitness.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.NavigateToLogin -> {
                viewModelScope.launch {
                    _events.send(SignUpEvent.NavigateBack(state.value.email, ""))
                }
            }
            is SignUpEvent.ShowSnackbar -> {
                // Handle ShowSnackbar event if needed
            }
            is SignUpEvent.NavigateBack -> {
                // Handle NavigateBack event if needed
            }

            else -> {}
        }
    }

    fun validateFullName(fullName: String) {
        _state.value = state.value.copy(
            fullName = fullName,
            fullNameError = if (fullName.length < 3) "Full name must be at least 3 characters" else null
        )
    }

    fun validateEmail(email: String) {
        val emailResult = validateEmailUseCase(email)
        _state.value = state.value.copy(
            email = email,
            emailError = when(emailResult) {
                is ValidationResult.Success -> null
                is ValidationResult.Error -> emailResult.errorMessage
            }
        )
    }

    fun validatePassword(password: String) {
        val passwordResult = validatePasswordUseCase(password)
        _state.value = state.value.copy(
            password = password,
            passwordError = when(passwordResult) {
                is ValidationResult.Success -> null
                is ValidationResult.Error -> passwordResult.errorMessage
            }
        )
    }

    fun validateConfirmPassword(confirmPassword: String) {
        _state.value = state.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = if (confirmPassword != state.value.password) "Passwords don't match" else null
        )
    }
}