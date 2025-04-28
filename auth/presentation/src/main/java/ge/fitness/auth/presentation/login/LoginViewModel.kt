package ge.fitness.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.auth.domain.auth.LoginUseCase
import ge.fitness.auth.domain.validation.ValidateEmailUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordUseCase
import ge.fitness.auth.domain.validation.ValidationResult
import ge.fitness.auth.presentation.utils.toStringRes
import ge.fitness.core.domain.model.DataStoreHelper
import ge.fitness.core.domain.model.DataStoreKeys
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
    private val loginUseCase: LoginUseCase,
    private val dataStoreHelper: DataStoreHelper
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
                isEmailValid = isEmailValid is ValidationResult.Success,
                email = email,
                emailError = isEmailValid.toStringRes(),
                isLoginEnabled = (isEmailValid is ValidationResult.Success) && (state.isPasswordValid)
            )
    }


    private fun validatePassword(password: String) {
        val isPasswordValid = validatePasswordUseCase(password)
        state = state.copy(
            isPasswordValid = isPasswordValid is ValidationResult.Success,
            passwordError = isPasswordValid.toStringRes(),
            password = password,
            isLoginEnabled = (isPasswordValid is ValidationResult.Success) && (state.isEmailValid)
        )
    }

    private suspend fun setLoginStatus(isLoggedIn: Boolean, rememberMe: Boolean) {
        dataStoreHelper.addPreference(DataStoreKeys.IsLoggedIn, isLoggedIn)
        dataStoreHelper.addPreference(DataStoreKeys.RememberMe, rememberMe)
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
                        setLoginStatus(isLoggedIn = true, rememberMe = state.rememberMe)

                        state = state.copy(isLoading = false)
                        _events.send(LoginEvent.LoginSuccess)
                    }
                }
            }
        }
    }
}