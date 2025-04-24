package ge.fitness.auth.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ge.fitness.auth.presentation.R
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.MomentumPasswordTextField
import ge.fitness.core.presentation.design_system.component.MomentumTextField
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.core.presentation.ui.HandleEvents


@Composable
fun SignUpScreenRoot(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateLogin: () -> Unit
) {
    val context = LocalContext.current
    HandleEvents(viewModel.events) { events ->
        when (events) {
            SignUpEvent.Success -> {
                onNavigateLogin()
            }

            is SignUpEvent.ShowError -> {
                Toast.makeText(
                    context,
                    events.error?.let { context.getString(it) },
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    SignUpScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                SignupAction.OnLoginClick -> onNavigateLogin()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun SignUpScreen(
    state: SignUpState,
    onAction: (SignupAction) -> Unit
) {
    MomentumTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("signupProgressIndicator")
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

                    IconButton(
                        onClick = { onAction(SignupAction.OnLoginClick) },
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
                            onValueChange = { onAction(SignupAction.OnFullNameChanged(it)) },
                            label = "Full name",
                            placeholder = "Enter your full name",
                            isError = state.fullNameError != null,
                            errorMessage = state.fullNameError
                        )

                        // Email field
                        MomentumTextField(
                            value = state.email,
                            onValueChange = { onAction(SignupAction.OnEmailChanged(it)) },
                            label = "Email",
                            placeholder = "Enter your email",
                            isError = state.emailError != null,
                            errorMessage = state.emailError?.let { stringResource(id = it) }
                        )

                        // Password field
                        MomentumPasswordTextField(
                            value = state.password,
                            onValueChange = { onAction(SignupAction.OnPasswordChanged(it)) },
                            label = "Password",
                            isPasswordVisible = state.isPasswordVisible,
                            placeholder = "Enter your password",
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError?.let { stringResource(id = it) },
                            onTogglePasswordVisibility = {
                                onAction(SignupAction.OnTogglePasswordVisibility)
                            }
                        )

                        // Confirm Password field
                        MomentumPasswordTextField(
                            value = state.confirmPassword,
                            onValueChange = { onAction(SignupAction.OnRepeatPasswordChanged(it)) },
                            label = "Confirm Password",
                            isPasswordVisible = state.isConfirmPasswordVisible,
                            placeholder = "Confirm your password",
                            isError = state.confirmPasswordError != null,
                            errorMessage = state.confirmPasswordError?.let { stringResource(id = it) },
                            onTogglePasswordVisibility = {
                                onAction(SignupAction.OnToggleConfirmPasswordVisibility)
                            }
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
                        onAction(
                            SignupAction.OnRegisterClick(
                                state.email,
                                state.password,
                                state.fullName
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    content = {
                        Text(
                            text = "Sign Up",
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
                        onClick = { onAction(SignupAction.OnLoginClick) },
                        contentPadding = PaddingValues(horizontal = 4.dp),
                        modifier = Modifier.testTag("loginNavButton")
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
        SignUpScreen(
            state = SignUpState(
                isRegisterEnabled = true
            )
        ){

        }
    }
}