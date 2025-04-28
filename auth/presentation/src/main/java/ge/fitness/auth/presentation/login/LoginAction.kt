package ge.fitness.auth.presentation.login

import android.content.Intent

sealed interface LoginAction {
    data class OnLoginClick(val email: String, val password: String) : LoginAction
    data object OnRegisterClick : LoginAction
    data object OnTogglePasswordVisibility : LoginAction
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPasswordChanged(val password: String) : LoginAction
    data class OnRememberMeChanged(val rememberMe: Boolean) : LoginAction
    data object OnGoogleSignInClick : LoginAction
    data class OnGoogleSignInResult(val idToken: String?) : LoginAction

    data object GetGoogleSignInIntent : LoginAction
    data class ProcessGoogleSignIn(val data: Intent?) : LoginAction
}