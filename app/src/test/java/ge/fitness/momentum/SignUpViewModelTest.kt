package ge.fitness.momentum

import ge.fitness.auth.domain.AuthRepository
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidateFullNameUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordMatchUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidationResult
import ge.fitness.auth.presentation.signup.SignUpEvent
import ge.fitness.auth.presentation.signup.SignUpViewModel
import ge.fitness.auth.presentation.signup.SignupAction
import ge.fitness.core.domain.User
import ge.fitness.core.domain.util.AuthError
import ge.fitness.core.domain.util.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var validatePasswordMatchUseCase: ValidatePasswordMatchUseCase
    private lateinit var validateFullNameUseCase: ValidateFullNameUseCase
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockUser = User(
        id = "user123",
        displayName = "Test User",
        isEmailVerified = true,
        email = "test@example.com"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        validateEmailUseCase = mockk(relaxed = true)
        validatePasswordUseCase = mockk(relaxed = true)
        validatePasswordMatchUseCase = mockk(relaxed = true)
        validateFullNameUseCase = mockk(relaxed = true)
        authRepository = mockk(relaxed = true)

        viewModel = SignUpViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
            validatePasswordMatchUseCase,
            validateFullNameUseCase,
            authRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when email changes and is valid, state is updated correctly`() {
        // Given
        val validEmail = "test@example.com"
        coEvery { validateEmailUseCase(validEmail) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnEmailChanged(validEmail))

        // Then
        assertEquals(validEmail, viewModel.state.email)
        assertTrue(viewModel.state.isValidEmail)  // Changed to assertTrue
        assertEquals(null, viewModel.state.emailError)
        assertTrue(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when email changes and is invalid, state is updated correctly`() {
        // Given
        val invalidEmail = "invalid-email"
        coEvery { validateEmailUseCase(invalidEmail) } returns ValidationResult.EmailError.INVALID_EMAIL

        // When
        viewModel.onAction(SignupAction.OnEmailChanged(invalidEmail))

        // Then
        assertEquals(invalidEmail, viewModel.state.email)
        assertFalse(viewModel.state.isValidEmail)  // Changed to assertFalse
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when password changes and is valid, state is updated correctly`() {
        // Given
        val validPassword = "Valid1Password"
        every { validatePasswordUseCase(validPassword) } returns ValidationResult.Success
        // For cases where confirm password validation is revalidated
        every { validatePasswordMatchUseCase(any(), any()) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnPasswordChanged(validPassword))

        // Then
        assertEquals(validPassword, viewModel.state.password)
        assertTrue(viewModel.state.isValidPassword) // Changed from assertFalse to assertTrue
        assertEquals(null, viewModel.state.passwordError)
        // The register button should be enabled if other validations pass, but here we're just testing password validation
    }


    @Test
    fun `when password changes and is invalid, state is updated correctly`() {
        // Given
        val invalidPassword = "weak"
        every { validatePasswordUseCase(invalidPassword) } returns ValidationResult.PasswordError.TOO_SHORT

        // When
        viewModel.onAction(SignupAction.OnPasswordChanged(invalidPassword))

        // Then
        assertEquals(invalidPassword, viewModel.state.password)
        assertFalse(viewModel.state.isValidPassword) // Changed from assertTrue to assertFalse
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when repeat password changes and matches, state is updated correctly`() {
        // Given
        val password = "Password123"
        val repeatPassword = "Password123"

        // Set initial password
        viewModel.onAction(SignupAction.OnPasswordChanged(password))

        // Mock validation
        every { validatePasswordMatchUseCase(password, repeatPassword) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnRepeatPasswordChanged(repeatPassword))

        // Then
        assertEquals(repeatPassword, viewModel.state.confirmPassword)
        assertEquals(null, viewModel.state.confirmPasswordError)
    }

    @Test
    fun `when repeat password changes and doesn't match, error is set`() {
        // Given
        val password = "Password123"
        val repeatPassword = "DifferentPassword123"

        // Set initial password
        every { validatePasswordUseCase(password) } returns ValidationResult.Success
        viewModel.onAction(SignupAction.OnPasswordChanged(password))

        // Mock validation for password match
        every { validatePasswordMatchUseCase(password, repeatPassword) } returns ValidationResult.PasswordMatchError.NO_MATCH

        // When
        viewModel.onAction(SignupAction.OnRepeatPasswordChanged(repeatPassword))

        // Then
        assertEquals(repeatPassword, viewModel.state.confirmPassword)
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when full name changes and is valid, state is updated correctly`() {
        // Given
        val fullName = "John Doe"
        every { validateFullNameUseCase(fullName) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnFullNameChanged(fullName))

        // Then
        assertEquals(fullName, viewModel.state.fullName)
        assertEquals(null, viewModel.state.fullNameError)
    }

    @Test
    fun `when full name changes and is invalid, error is set`() {
        // Given
        val invalidFullName = "J"  // Too short
        every { validateFullNameUseCase(invalidFullName) } returns ValidationResult.FullNameError.TOO_SHORT

        // When
        viewModel.onAction(SignupAction.OnFullNameChanged(invalidFullName))

        // Then
        assertEquals(invalidFullName, viewModel.state.fullName)
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when register is successful, Success event is emitted`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "Password123"
        val fullName = "John Doe"

        // Set up the mock BEFORE calling the action
        coEvery { authRepository.signUp(email, password, fullName) } returns Resource.Success(mockUser)

        // When
        viewModel.onAction(SignupAction.OnRegisterClick(email, password, fullName))

        // Then
        val event = viewModel.events.first()
        assertTrue(event is SignUpEvent.Success)
    }

    @Test
    fun `when register fails, ShowError event is emitted`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "Password123"
        val fullName = "John Doe"
        val signUpError = AuthError.RegisterError.USER_ALREADY_EXISTS

        // Mock repository to return error response
        coEvery { authRepository.signUp(email, password, fullName) } returns Resource.Error(signUpError)

        // When
        viewModel.onAction(SignupAction.OnRegisterClick(email, password, fullName))

        // Then - Use suspending first() to get the first emitted event
        val event = viewModel.events.first()
        assertTrue(event is SignUpEvent.ShowError)
    }

    @Test
    fun `when register is in progress, isLoading is set to true`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "Password123"
        val fullName = "John Doe"

        // Set up the mock BEFORE calling the action
        coEvery { authRepository.signUp(email, password, fullName) } returns Resource.Loading

        // When
        viewModel.onAction(SignupAction.OnRegisterClick(email, password, fullName))

        // Then
        assertTrue(viewModel.state.isLoading)
    }

    @Test
    fun `validateEmail calls validateEmailUseCase`() {
        // Given
        val email = "test@example.com"
        coEvery { validateEmailUseCase(email) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnEmailChanged(email))

        // Then
        verify { validateEmailUseCase(email) }
    }

    @Test
    fun `validatePassword calls validatePasswordUseCase`() {
        // Given
        val password = "Password123"
        coEvery { validatePasswordUseCase(password) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnPasswordChanged(password))

        // Then
        verify { validatePasswordUseCase(password) }
    }

    @Test
    fun `validatePasswordMatch calls validatePasswordMatchUseCase`() {
        // Given
        val password = "Password123"
        val confirmPassword = "Password123"

        // Set initial password
        viewModel.onAction(SignupAction.OnPasswordChanged(password))

        every { validatePasswordMatchUseCase(password, confirmPassword) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnRepeatPasswordChanged(confirmPassword))

        // Then
        verify { validatePasswordMatchUseCase(password, confirmPassword) }
    }

    @Test
    fun `validateFullName calls validateFullNameUseCase`() {
        // Given
        val fullName = "John Doe"
        every { validateFullNameUseCase(fullName) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnFullNameChanged(fullName))

        // Then
        verify { validateFullNameUseCase(fullName) }
    }

    @Test
    fun `toggle password visibility works correctly`() {
        // Initial state should be not visible
        assertFalse(viewModel.state.isPasswordVisible)

        // When
        viewModel.onAction(SignupAction.OnTogglePasswordVisibility)

        // Then
        assertTrue(viewModel.state.isPasswordVisible)

        // Toggle back
        viewModel.onAction(SignupAction.OnTogglePasswordVisibility)

        // Should be not visible again
        assertFalse(viewModel.state.isPasswordVisible)
    }

    @Test
    fun `toggle confirm password visibility works correctly`() {
        // Initial state should be not visible
        assertFalse(viewModel.state.isConfirmPasswordVisible)

        // When
        viewModel.onAction(SignupAction.OnToggleConfirmPasswordVisibility)

        // Then
        assertTrue(viewModel.state.isConfirmPasswordVisible)

        // Toggle back
        viewModel.onAction(SignupAction.OnToggleConfirmPasswordVisibility)

        // Should be not visible again
        assertFalse(viewModel.state.isConfirmPasswordVisible)
    }
}