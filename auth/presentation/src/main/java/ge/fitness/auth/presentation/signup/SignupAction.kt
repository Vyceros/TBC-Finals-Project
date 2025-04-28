package ge.fitness.auth.presentation.signup

import android.content.Intent

sealed interface SignupAction {
    data object OnLoginClick : SignupAction
    data class OnRegisterClick(val email: String, val password: String, val fullName: String) : SignupAction
    data class OnFullNameChanged(val name: String) : SignupAction
    data class OnEmailChanged(val email: String) : SignupAction
    data class OnPasswordChanged(val password: String) : SignupAction
    data class OnRepeatPasswordChanged(val repeatPassword: String) : SignupAction
    data object OnTogglePasswordVisibility : SignupAction
    data object OnToggleConfirmPasswordVisibility : SignupAction
    data object OnGoogleSignInClick : SignupAction

    data object GetGoogleSignInIntent : SignupAction
    data class ProcessGoogleSignIn(val data: Intent?) : SignupAction
}

