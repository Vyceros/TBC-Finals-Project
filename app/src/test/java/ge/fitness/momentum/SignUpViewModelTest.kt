package ge.fitness.momentum

import ge.fitness.auth.domain.AuthRepository
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidationResult
import ge.fitness.auth.presentation.signup.SignUpEvent
import ge.fitness.auth.presentation.signup.SignUpViewModel
import ge.fitness.auth.presentation.signup.SignupAction
import ge.fitness.core.domain.User
import ge.fitness.core.domain.util.AuthError
import ge.fitness.core.domain.util.Resource
import io.mockk.coEvery
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
        validateEmailUseCase = mockk()
        validatePasswordUseCase = mockk()
        authRepository = mockk()
        viewModel = SignUpViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
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
        assertFalse(viewModel.state.isValidEmail)
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
        assertTrue(viewModel.state.isValidEmail)
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when password changes and is valid, state is updated correctly`() {
        // Given
        val validPassword = "Valid1Password"
        coEvery { validatePasswordUseCase(validPassword) } returns ValidationResult.Success

        // When
        viewModel.onAction(SignupAction.OnPasswordChanged(validPassword))

        // Then
        assertEquals(validPassword, viewModel.state.password)
        assertFalse(viewModel.state.isValidPassword)
        assertEquals(null, viewModel.state.passwordError)
        assertTrue(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when password changes and is invalid, state is updated correctly`() {
        // Given
        val invalidPassword = "weak"
        coEvery { validatePasswordUseCase(invalidPassword) } returns ValidationResult.PasswordError.TOO_SHORT

        // When
        viewModel.onAction(SignupAction.OnPasswordChanged(invalidPassword))

        // Then
        assertEquals(invalidPassword, viewModel.state.password)
        assertTrue(viewModel.state.isValidPassword)
        assertFalse(viewModel.state.isRegisterEnabled)
    }

    @Test
    fun `when repeat password changes, state is updated correctly`() {
        // Given
        val repeatPassword = "Password123"

        // When
        viewModel.onAction(SignupAction.OnRepeatPasswordChanged(repeatPassword))

        // Then
        assertEquals(repeatPassword, viewModel.state.confirmPassword)
    }

    @Test
    fun `when full name changes, state is updated correctly`() {
        // Given
        val fullName = "John Doe"

        // When
        viewModel.onAction(SignupAction.OnFullNameChanged(fullName))

        // Then
        assertEquals(fullName, viewModel.state.fullName)
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
        val signUpError = AuthError.RegisterError.USER_ALREADY_EXISTS  // Fix this line

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
}