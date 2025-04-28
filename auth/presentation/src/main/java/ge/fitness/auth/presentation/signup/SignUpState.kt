package ge.fitness.auth.presentation.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordMatch : Boolean = false,
    val isValidPassword : Boolean = false,
    val isValidEmail : Boolean = false,
    val fullNameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isRegisterEnabled : Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isGoogleSignInLoading: Boolean = false,
)