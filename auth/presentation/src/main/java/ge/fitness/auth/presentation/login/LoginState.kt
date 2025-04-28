package ge.fitness.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val rememberMe: Boolean = false,
    val isPasswordVisible : Boolean = false,
    val isGoogleSignInLoading: Boolean = false,
)