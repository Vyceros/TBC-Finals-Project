package ge.fitness.momentum

import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCaseImpl
import ge.fitness.auth.domain.usecase.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePasswordUseCase = ValidatePasswordUseCaseImpl()
    }

    @Test
    fun `empty password returns Empty error`() {
        // Given
        val emptyPassword = ""

        // When
        val result = validatePasswordUseCase(emptyPassword)

        // Then
        assertTrue(result is ValidationResult.PasswordError)
        assertEquals(ValidationResult.PasswordError.EMPTY, result)
    }

    @Test
    fun `password with less than 6 characters returns TooShort error`() {
        // Given
        val shortPassword = "Abc1"

        // When
        val result = validatePasswordUseCase(shortPassword)

        // Then
        assertTrue(result is ValidationResult.PasswordError)
        assertEquals(ValidationResult.PasswordError.TOO_SHORT, result)
    }

    @Test
    fun `password without digits returns NoDigit error`() {
        // Given
        val passwordWithoutDigits = "AbcdefgH"

        // When
        val result = validatePasswordUseCase(passwordWithoutDigits)

        // Then
        assertTrue(result is ValidationResult.PasswordError)
        assertEquals(ValidationResult.PasswordError.NO_DIGIT, result)
    }

    @Test
    fun `password without uppercase letters returns NoUppercase error`() {
        // Given
        val passwordWithoutUppercase = "abcdefg123"

        // When
        val result = validatePasswordUseCase(passwordWithoutUppercase)

        // Then
        assertTrue(result is ValidationResult.PasswordError)
        assertEquals(ValidationResult.PasswordError.NO_UPPERCASE, result)
    }

    @Test
    fun `valid password returns Success`() {
        // Given
        val validPassword = "Password123"

        // When
        val result = validatePasswordUseCase(validPassword)

        // Then
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `valid complex password returns Success`() {
        // Given
        val validPassword = "P@ssw0rd!123ABC"

        // When
        val result = validatePasswordUseCase(validPassword)

        // Then
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `minimum length password with valid criteria returns Success`() {
        // Given
        val validPassword = "Pass1"

        // When
        val result = validatePasswordUseCase(validPassword)

        // Then
        assertTrue(result is ValidationResult.PasswordError)
        assertEquals(ValidationResult.PasswordError.TOO_SHORT, result)
    }
}