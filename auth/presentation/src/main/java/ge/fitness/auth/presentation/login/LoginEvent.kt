package ge.fitness.auth.presentation.login

sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    data class OnRememberMeChanged(val rememberMe: Boolean) : LoginEvent()
    data class LoginUser(val email: String, val password: String, val rememberMe: Boolean) : LoginEvent()
    data object CheckUserSession : LoginEvent()
    data object NavigateToHome : LoginEvent()
    data class ShowSnackbar(val message: String) : LoginEvent()
}