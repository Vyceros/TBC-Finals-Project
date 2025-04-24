package ge.fitness.auth.presentation.signup

sealed interface SignupAction {
    data object OnLoginClick : SignupAction
    data class OnRegisterClick(val email: String, val password: String, val fullName: String) : SignupAction
    data class OnFullNameChanged(val name: String) : SignupAction
    data class OnEmailChanged(val email: String) : SignupAction
    data class OnPasswordChanged(val password: String) : SignupAction
    data class OnRepeatPasswordChanged(val repeatPassword: String) : SignupAction
    data object OnTogglePasswordVisibility : SignupAction
    data object OnToggleConfirmPasswordVisibility : SignupAction
}

