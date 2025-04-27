package ge.fitness.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.auth.SignUpUseCase
import ge.fitness.auth.domain.validation.ValidateEmailUseCase
import ge.fitness.auth.domain.validation.ValidateFullNameUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordMatchUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordUseCase
import ge.fitness.auth.domain.validation.ValidationResult
import ge.fitness.auth.presentation.utils.toStringRes
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordMatchUseCase: ValidatePasswordMatchUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SignupAction) {
        when (action) {
            is SignupAction.OnEmailChanged -> onEmailChanged(action.email)
            is SignupAction.OnFullNameChanged -> onFullNameChanged(action.name)
            is SignupAction.OnPasswordChanged -> onPasswordChanged(action.password)
            is SignupAction.OnRepeatPasswordChanged ->
                onRepeatPasswordChanged(action.repeatPassword)

            is SignupAction.OnRegisterClick -> register(
                action.email,
                action.password,
                action.fullName
            )

            SignupAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            SignupAction.OnToggleConfirmPasswordVisibility -> {
                state = state.copy(
                    isConfirmPasswordVisible = !state.isConfirmPasswordVisible
                )
            }

            else -> Unit
        }
    }


    private fun onEmailChanged(email: String) {
        val isEmailValid = validateEmailUseCase(email)
        state =
            state.copy(
                isValidEmail = isEmailValid is ValidationResult.Success,
                email = email,
                emailError = isEmailValid.toStringRes(),
                isRegisterEnabled = isEmailValid is ValidationResult.Success
            )
    }


    private fun onPasswordChanged(password: String) {
        val isPasswordValid = validatePasswordUseCase(password)

        val passwordMatchResult = if (state.confirmPassword.isNotEmpty()) {
            validatePasswordMatchUseCase(password, state.confirmPassword)
        } else {
            null
        }

        state = state.copy(
            isValidPassword = isPasswordValid is ValidationResult.Success,
            passwordError = isPasswordValid.toStringRes(),
            password = password,
            confirmPasswordError = passwordMatchResult?.toStringRes(),
            isRegisterEnabled = isPasswordValid is ValidationResult.Success &&
                    (passwordMatchResult == null || passwordMatchResult is ValidationResult.Success) &&
                    state.isValidEmail
        )
    }


    private fun onRepeatPasswordChanged(password: String) {
        val passwordMatchResult = validatePasswordMatchUseCase(state.password, password)

        state = state.copy(
            confirmPassword = password,
            confirmPasswordError = passwordMatchResult.toStringRes(),
            isRegisterEnabled = passwordMatchResult is ValidationResult.Success &&
                    state.isValidEmail &&
                    state.isValidPassword
        )
    }

    private fun onFullNameChanged(name: String) {
        val fullNameValidation = validateFullNameUseCase(name)

        state = state.copy(
            fullName = name,
            fullNameError = fullNameValidation.toStringRes(),
            isRegisterEnabled = fullNameValidation is ValidationResult.Success &&
                    state.isValidEmail &&
                    state.isValidPassword &&
                    (state.confirmPassword.isEmpty() || state.confirmPasswordError == null)
        )
    }


    private fun register(email: String, password: String, fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase(email, password, fullName).collectLatest { response ->
                when (response) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        _events.send(SignUpEvent.ShowError(response.error.toStringRes()))
                    }

                    Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                        _events.send(SignUpEvent.Success)
                    }
                }

            }
        }
    }


}