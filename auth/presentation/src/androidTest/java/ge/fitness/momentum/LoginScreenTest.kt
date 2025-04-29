package ge.fitness.momentum

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ge.fitness.auth.presentation.login.LoginScreen
import ge.fitness.auth.presentation.login.LoginState
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysAllElements() {
        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Remember me").assertIsDisplayed()
        composeTestRule.onNodeWithText("Forgot Password").assertIsDisplayed()

        composeTestRule.onNodeWithTag("loginButton").assertIsDisplayed()
    }

    @Test
    fun loginScreen_canEnterCredentials() {
        // Given
        val testEmail = "test@example.com"
        val testPassword = "Password123"

        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("emailField").performClick()
        composeTestRule.onNodeWithText("Enter your email").performTextInput(testEmail)

        composeTestRule.onNodeWithTag("passwordField").performClick()
        composeTestRule.onNodeWithText("Enter your password").performTextInput(testPassword)
    }

    @Test
    fun loginScreen_rememberMeToggle() {
        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Remember me").performClick()
    }

    @Test
    fun loginScreen_loginButtonEnabled_whenCredentialsValid() {
        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(
                    email = "test@example.com",
                    password = "Password123",
                    isLoginEnabled = true
                ),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("loginButton").assertIsEnabled()
    }

    @Test
    fun loginScreen_loginButtonDisabled_whenCredentialsInvalid() {
        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(
                    email = "invalid-email",
                    password = "weak",
                    isLoginEnabled = false
                ),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("loginButton").assertIsNotEnabled()
    }

    @Test
    fun loginScreen_showsLoadingIndicator_whenLoading() {
        // When
        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(isLoading = true),
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("loginProgressIndicator").assertIsDisplayed()
    }
}