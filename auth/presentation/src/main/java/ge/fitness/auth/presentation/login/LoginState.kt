package ge.fitness.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val rememberMe: Boolean = false
)