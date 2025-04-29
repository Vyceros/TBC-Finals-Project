package ge.fitness.momentum

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ge.fitness.auth.presentation.signup.SignUpScreen
import ge.fitness.auth.presentation.signup.SignUpState
import ge.fitness.auth.presentation.signup.SignupAction
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signUpScreen_displaysAllElements() {
        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(),
                onAction = {},
                onBackClick = {}
            )
        }

        composeTestRule.onNodeWithText("Create Account").assertExists()
        composeTestRule.onNodeWithText("Let's Start!").assertExists()

        composeTestRule.onNodeWithText("Full name").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Confirm Password").assertExists()

        composeTestRule.onNodeWithText("Sign Up").assertExists()
        composeTestRule.onNodeWithText("Already have an account? ").assertExists()
        composeTestRule.onNodeWithText("Log in").assertExists()

        composeTestRule.onNodeWithText("By continuing, you agree to", substring = true)
            .assertExists()
    }

    @Test
    fun signUpScreen_canEnterUserDetails() {
        // Given
        val testFullName = "John Doe"
        val testEmail = "john.doe@example.com"
        val testPassword = "Password123"
        val testConfirmPassword = "Password123"

        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(),
                onAction = {},
                onBackClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Enter your full name").performTextInput(testFullName)
        composeTestRule.onNodeWithText("Enter your email").performTextInput(testEmail)
        composeTestRule.onNodeWithText("Enter your password").performTextInput(testPassword)
        composeTestRule.onNodeWithText("Confirm your password")
            .performTextInput(testConfirmPassword)
    }

    @Test
    fun signUpScreen_signUpButtonEnabled_whenAllFieldsValid() {
        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    fullName = "John Doe",
                    email = "test@example.com",
                    password = "Password123",
                    confirmPassword = "Password123",
                    isRegisterEnabled = true
                ),
                onAction = {},
                onBackClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Sign Up").assertIsEnabled()
    }

    @Test
    fun signUpScreen_signUpButtonDisabled_whenFieldsInvalid() {
        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    fullName = "",
                    email = "invalid-email",
                    password = "weak",
                    confirmPassword = "different",
                    isRegisterEnabled = false
                ),
                onAction = {},
                onBackClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Sign Up").assertIsNotEnabled()
    }

    @Test
    fun signUpScreen_showsLoadingIndicator_whenLoading() {
        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(isLoading = true),
                onAction = {},
                onBackClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("signupProgressIndicator").assertExists()
    }

    @Test
    fun signUpScreen_loginButtonNavigation() {
        var actionTriggered = false

        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(),
                onAction = { action ->
                    if (action == SignupAction.OnLoginClick) {
                        actionTriggered = true
                    }
                },
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("loginNavButton")
            .assertHasClickAction()
            .assertIsEnabled()
            .performClick()

        composeTestRule.runOnIdle {
            assert(actionTriggered) {
                "Login button click did not trigger the expected action"
            }
        }
    }


    @Test
    fun signUpScreen_registerButtonTriggersAction() {
        // Given
        var registerActionCalled = false
        val testEmail = "test@example.com"
        val testPassword = "Password123"
        val testFullName = "John Doe"

        // When
        composeTestRule.setContent {
            SignUpScreen(
                state = SignUpState(
                    fullName = testFullName,
                    email = testEmail,
                    password = testPassword,
                    confirmPassword = testPassword,
                    isRegisterEnabled = true
                ),
                onAction = { action ->
                    if (action is SignupAction.OnRegisterClick) {
                        if (action.email == testEmail &&
                            action.password == testPassword &&
                            action.fullName == testFullName
                        ) {
                            registerActionCalled = true
                        }
                    }
                },
                onBackClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Sign Up").performClick()
        assert(registerActionCalled) { "Register button click did not trigger the expected action" }
    }
}