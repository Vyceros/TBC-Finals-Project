package ge.fitness.auth.presentation.login

sealed interface LoginAction {
    data class OnLoginClick(val email: String, val password: String) : LoginAction
    data object OnRegisterClick : LoginAction
    data object OnTogglePasswordVisibility : LoginAction
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPasswordChanged(val password: String) : LoginAction
    data class OnRememberMeChanged(val rememberMe: Boolean) : LoginAction
}