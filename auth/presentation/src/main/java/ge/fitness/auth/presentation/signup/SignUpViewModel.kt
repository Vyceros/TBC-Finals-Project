package ge.fitness.auth.presentation.signup

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.auth.domain.auth.GoogleSignInManager
import ge.fitness.auth.domain.auth.SignInWithGoogleUseCase
import ge.fitness.auth.domain.auth.SignUpUseCase
import ge.fitness.auth.domain.validation.ValidateEmailUseCase
import ge.fitness.auth.domain.validation.ValidateFullNameUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordMatchUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordUseCase
import ge.fitness.auth.domain.validation.ValidationResult
import ge.fitness.auth.presentation.utils.toStringRes
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.auth.GoogleUser
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
    private val signUpUseCase: SignUpUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleSignInManager: GoogleSignInManager
) : ViewModel() {

    @Inject
    lateinit var authManager: AuthManager

    var state by mutableStateOf(SignUpState())
        private set

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SignupAction): Any? {
        when (action) {
            is SignupAction.OnEmailChanged -> validateEmail(action.email)
            is SignupAction.OnFullNameChanged -> validateFullName(action.name)
            is SignupAction.OnPasswordChanged -> validatePassword(action.password)
            is SignupAction.OnRepeatPasswordChanged -> validateRepeatPassword(action.repeatPassword)
            SignupAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            SignupAction.OnToggleConfirmPasswordVisibility -> {
                state = state.copy(isConfirmPasswordVisible = !state.isConfirmPasswordVisible)
            }
            is SignupAction.OnRegisterClick -> register(action.email, action.password, action.fullName)
            SignupAction.GetGoogleSignInIntent -> {
                return googleSignInManager.getSignInRequest().asPlatformRequest<Intent>()
            }
            is SignupAction.ProcessGoogleSignIn -> {
                val idToken = googleSignInManager.getIdTokenFromResult(action.data)
                if (idToken == null) {
                    state = state.copy(isGoogleSignInLoading = false)
                    viewModelScope.launch {
                        _events.send(SignUpEvent.ShowError(null))
                    }
                } else {
                    state = state.copy(isGoogleSignInLoading = true)
                    handleGoogleSignIn(idToken)
                }
            }

            SignupAction.OnLoginClick -> {}
            SignupAction.OnGoogleSignInClick -> {}
        }
        return null
    }

    private fun validateEmail(email: String) {
        val emailValidation = validateEmailUseCase(email)
        state = state.copy(
            email = email,
            isValidEmail = emailValidation is ValidationResult.Success,
            emailError = emailValidation.toStringRes(),
            isRegisterEnabled = updateRegisterButtonState(
                emailValid = emailValidation is ValidationResult.Success
            )
        )
    }

    private fun validatePassword(password: String) {
        val passwordValidation = validatePasswordUseCase(password)
        val passwordMatchValidation = if (state.confirmPassword.isNotEmpty()) {
            validatePasswordMatchUseCase(password, state.confirmPassword)
        } else null

        state = state.copy(
            password = password,
            isValidPassword = passwordValidation is ValidationResult.Success,
            passwordError = passwordValidation.toStringRes(),
            confirmPasswordError = passwordMatchValidation?.toStringRes(),
            isRegisterEnabled = updateRegisterButtonState(
                passwordValid = passwordValidation is ValidationResult.Success,
                passwordsMatch = passwordMatchValidation is ValidationResult.Success || passwordMatchValidation == null
            )
        )
    }

    private fun validateRepeatPassword(repeatPassword: String) {
        val passwordMatchValidation = validatePasswordMatchUseCase(state.password, repeatPassword)
        state = state.copy(
            confirmPassword = repeatPassword,
            confirmPasswordError = passwordMatchValidation.toStringRes(),
            isRegisterEnabled = updateRegisterButtonState(
                passwordsMatch = passwordMatchValidation is ValidationResult.Success
            )
        )
    }

    private fun validateFullName(name: String) {
        val fullNameValidation = validateFullNameUseCase(name)
        state = state.copy(
            fullName = name,
            fullNameError = fullNameValidation.toStringRes(),
            isRegisterEnabled = updateRegisterButtonState(
                fullNameValid = fullNameValidation is ValidationResult.Success
            )
        )
    }

    private fun updateRegisterButtonState(
        emailValid: Boolean = state.isValidEmail,
        passwordValid: Boolean = state.isValidPassword,
        passwordsMatch: Boolean = state.confirmPasswordError == null,
        fullNameValid: Boolean = state.fullNameError == null
    ): Boolean {
        return emailValid && passwordValid && passwordsMatch && fullNameValid
    }

    private fun register(email: String, password: String, fullName: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase(email, password, fullName).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        _events.send(SignUpEvent.ShowError(response.error.toStringRes()))
                    }
                    Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        // We don't automatically save session on regular registration
                        state = state.copy(isLoading = false)
                        _events.send(SignUpEvent.Success)
                    }
                }
            }
        }
    }

    private fun handleGoogleSignIn(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signInWithGoogleUseCase(idToken).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        state = state.copy(isGoogleSignInLoading = false)
                        _events.send(SignUpEvent.ShowError(response.error.toStringRes()))
                    }
                    Resource.Loading -> {
                        state = state.copy(isGoogleSignInLoading = true)
                    }
                    is Resource.Success -> {
                        // For Google sign-in, we always save the session
                        val googleUser = response.data
                        authManager.saveUserSession(googleUser.token)
                        state = state.copy(isGoogleSignInLoading = false)
                        _events.send(SignUpEvent.Success)
                    }
                }
            }
        }
    }
}