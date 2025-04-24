package ge.fitness.momentum

import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidateEmailUseCaseImpl
import ge.fitness.auth.domain.usecase.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCaseImpl()
    }

    @Test
    fun `valid email returns Success`() {
        // Given
        val validEmail = "test@example.com"

        // When
        val result = validateEmailUseCase(validEmail)

        // Then
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `email without @ returns InvalidEmail`() {
        // Given
        val invalidEmail = "testexample.com"

        // When
        val result = validateEmailUseCase(invalidEmail)

        // Then
        assertTrue(result is ValidationResult.EmailError)
        assertEquals(ValidationResult.EmailError.INVALID_EMAIL, result)
    }

    @Test
    fun `email without domain returns InvalidEmail`() {
        // Given
        val invalidEmail = "test@"

        // When
        val result = validateEmailUseCase(invalidEmail)

        // Then
        assertTrue(result is ValidationResult.EmailError)
        assertEquals(ValidationResult.EmailError.INVALID_EMAIL, result)
    }

    @Test
    fun `email with invalid TLD returns InvalidEmail`() {
        // Given
        val invalidEmail = "test@example."

        // When
        val result = validateEmailUseCase(invalidEmail)

        // Then
        assertTrue(result is ValidationResult.EmailError)
        assertEquals(ValidationResult.EmailError.INVALID_EMAIL, result)
    }

    @Test
    fun `email with short TLD returns InvalidEmail`() {
        // Given
        val invalidEmail = "test@example.a"

        // When
        val result = validateEmailUseCase(invalidEmail)

        // Then
        assertTrue(result is ValidationResult.EmailError)
        assertEquals(ValidationResult.EmailError.INVALID_EMAIL, result)
    }

    @Test
    fun `email with spaces returns InvalidEmail`() {
        // Given
        val invalidEmail = "test user@example.com"

        // When
        val result = validateEmailUseCase(invalidEmail)

        // Then
        assertTrue(result is ValidationResult.EmailError)
        assertEquals(ValidationResult.EmailError.INVALID_EMAIL, result)
    }

    @Test
    fun `complex but valid email returns Success`() {
        // Given
        val validEmail = "test.user+filter123@sub.example.co.uk"

        // When
        val result = validateEmailUseCase(validEmail)

        // Then
        assertTrue(result is ValidationResult.Success)
    }
}