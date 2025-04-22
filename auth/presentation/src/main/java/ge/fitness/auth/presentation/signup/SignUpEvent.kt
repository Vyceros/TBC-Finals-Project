package ge.fitness.auth.presentation.signup

sealed class SignUpEvent {
    data class RegisterUser(val fullName: String, val email: String, val password: String) : SignUpEvent()
    data class NavigateBack(val email: String, val password: String) : SignUpEvent()
    data class ShowSnackbar(val message: String) : SignUpEvent()
    data object NavigateToLogin : SignUpEvent()
}