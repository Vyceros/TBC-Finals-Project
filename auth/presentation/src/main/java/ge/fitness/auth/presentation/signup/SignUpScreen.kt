package ge.fitness.auth.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ge.fitness.auth.presentation.R
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.MomentumPasswordTextField
import ge.fitness.core.presentation.design_system.component.MomentumTextField
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.core.presentation.design_system.theme.MomentumTheme

@Composable
fun SignUpScreen(
    state: SignUpState = SignUpState(),
    onSignUp: (String, String, String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onFullNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {}
) {
    MomentumTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Toolbar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // Back button
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_button),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // Title
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Let's Start!
                Text(
                    text = "Let's Start!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Form section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Full name field
                        MomentumTextField(
                            value = state.fullName,
                            onValueChange = onFullNameChange,
                            label = "Full name",
                            placeholder = "Enter your full name",
                            isError = state.fullNameError != null,
                            errorMessage = state.fullNameError
                        )

                        // Email field
                        MomentumTextField(
                            value = state.email,
                            onValueChange = onEmailChange,
                            label = "Email",
                            placeholder = "Enter your email",
                            isError = state.emailError != null,
                            errorMessage = state.emailError
                        )

                        // Password field
                        MomentumPasswordTextField(
                            value = state.password,
                            onValueChange = onPasswordChange,
                            label = "Password",
                            placeholder = "Enter your password",
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError
                        )

                        // Confirm Password field
                        MomentumPasswordTextField(
                            value = state.confirmPassword,
                            onValueChange = onConfirmPasswordChange,
                            label = "Confirm Password",
                            placeholder = "Confirm your password",
                            isError = state.confirmPasswordError != null,
                            errorMessage = state.confirmPasswordError
                        )
                    }
                }

                // Terms and Privacy section
                Text(
                    buildAnnotatedString {
                        append("By continuing, you agree to ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Terms of Use")
                        }
                        append(" and ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Privacy Policy")
                        }
                        append(".")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Sign Up button with black text
                OutlinedMomentumButton(
                    onClick = {
                        onSignUp(state.fullName, state.email, state.password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    content = {
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.background
                        )
                    },
                    isEnabled = state.fullNameError == null &&
                            state.emailError == null &&
                            state.passwordError == null &&
                            state.confirmPasswordError == null &&
                            state.fullName.isNotBlank() &&
                            state.email.isNotBlank() &&
                            state.password.isNotBlank() &&
                            state.confirmPassword.isNotBlank()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // or sign up with
                Text(
                    text = "or sign up with",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Social login options
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Google login button
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gmail),
                            contentDescription = "Google Login",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Facebook login button
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Facebook Login",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Already have an account
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    TextButton(
                        onClick = onLoginClick,
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "Log in",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@AppPreview
@Composable
fun SignUpScreenPreview() {
    MomentumTheme {
        SignUpScreen()
    }
}