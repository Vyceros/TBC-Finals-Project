package ge.fitness.auth.presentation.login

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.auth.domain.auth.GoogleSignInManager
import ge.fitness.auth.domain.auth.LoginUseCase
import ge.fitness.auth.domain.auth.SignInWithGoogleUseCase
import ge.fitness.auth.domain.validation.ValidateEmailUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordUseCase
import ge.fitness.auth.domain.validation.ValidationResult
import ge.fitness.auth.presentation.utils.toStringRes
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.auth.GoogleUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleSignInManager: GoogleSignInManager
) : ViewModel() {

    @Inject
    lateinit var authManager: AuthManager

    var state by mutableStateOf(LoginState())
        private set

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction): Any? {
        return when (action) {
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

            LoginAction.GetGoogleSignInIntent -> {
                googleSignInManager.getSignInRequest().asPlatformRequest<Intent>()
            }

            is LoginAction.ProcessGoogleSignIn -> {
                val idToken = googleSignInManager.getIdTokenFromResult(action.data)
                if (idToken.isNullOrBlank()) {
                    viewModelScope.launch {
                        _events.send(LoginEvent.ShowError(null))
                    }
                } else {
                    state = state.copy(isGoogleSignInLoading = true)
                    handleGoogleSignIn(idToken)
                }
            }

            is LoginAction.OnGoogleSignInResult -> {
                action.idToken?.let { handleGoogleSignIn(it) }
            }

            LoginAction.OnRegisterClick,
            LoginAction.OnGoogleSignInClick -> {}
        }
    }

    private fun validateEmail(email: String) {
        val emailValidation = validateEmailUseCase(email)
        state = state.copy(
            email = email,
            isEmailValid = emailValidation is ValidationResult.Success,
            emailError = emailValidation.toStringRes(),
            isLoginEnabled = (emailValidation is ValidationResult.Success) && (state.isPasswordValid)
        )
    }

    private fun validatePassword(password: String) {
        val passwordValidation = validatePasswordUseCase(password)
        state = state.copy(
            password = password,
            isPasswordValid = passwordValidation is ValidationResult.Success,
            passwordError = passwordValidation.toStringRes(),
            isLoginEnabled = (passwordValidation is ValidationResult.Success) && (state.isEmailValid)
        )
    }

    private fun login(email: String, password: String) {
        state = state.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(email, password).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        _events.send(LoginEvent.ShowError(response.error.toStringRes()))
                    }
                    Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        // Important: Make sure we're manually signing out from Firebase after login
                        // This prevents Firebase from automatically persisting the session
                        // We'll clear Firebase's auto-persistence but still send success to navigate to the next screen
                        googleSignInManager.signOut() // This should sign out from Firebase

                        state = state.copy(isLoading = false)
                        _events.send(LoginEvent.LoginSuccess)
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
                        _events.send(LoginEvent.ShowError(response.error.toStringRes()))
                    }
                    Resource.Loading -> {
                        state = state.copy(isGoogleSignInLoading = true)
                    }
                    is Resource.Success -> {
                        val googleUser = response.data
                        authManager.saveUserSession(googleUser.token)
                        state = state.copy(isGoogleSignInLoading = false)
                        _events.send(LoginEvent.LoginSuccess)
                    }
                }
            }
        }
    }
}