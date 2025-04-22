package ge.fitness.auth.presentation.login

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ge.fitness.auth.presentation.R
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.MomentumButton
import ge.fitness.core.presentation.design_system.component.MomentumPasswordTextField
import ge.fitness.core.presentation.design_system.component.MomentumTextField
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.core.presentation.design_system.theme.MomentumTheme

@Composable
fun LoginScreen(
    state: LoginState,
    email: String? = null,
    password: String? = null,
    onLogin: (String, String, Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    var emailState by remember { mutableStateOf(email ?: "") }
    var passwordState by remember { mutableStateOf(password ?: "") }
    var rememberMe by remember { mutableStateOf(false) }

    // Update email and password if they come from navigation
    LaunchedEffect(email, password) {
        email?.let { emailState = it }
        password?.let { passwordState = it }
    }

    // Apply validation from ViewModel state
    LaunchedEffect(emailState) {
        onEmailChange(emailState)
    }

    LaunchedEffect(passwordState) {
        onPasswordChange(passwordState)
    }

    MomentumTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.5f))

                Text(
                    text = "Log In",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(0.5f))

                // Welcome text
                Text(
                    text = "Welcome",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login form section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                    ) {
                        MomentumTextField(
                            value = emailState,
                            onValueChange = { emailState = it },
                            label = "Email",
                            placeholder = "Enter your email",
                            isError = state.emailError != null,
                            errorMessage = state.emailError,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        MomentumPasswordTextField(
                            value = passwordState,
                            onValueChange = { passwordState = it },
                            label = "Password",
                            placeholder = "Enter your password",
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Remember me checkbox
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it }
                            )

                            Text(
                                text = "Remember me",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = { /* TODO: Implement forgot password */ }) {
                                Text(
                                    text = "Forgot Password?",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedMomentumButton(
                    onClick = { onLogin(emailState, passwordState, rememberMe) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    isEnabled = state.isLoginEnabled && !state.isLoading,
                    content = {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.background
                            )
                        } else {
                            Text(
                                text = "Log In",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Social login options
                Text(
                    text = "or sign up with",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp)
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

                // Added "Don't have an account? Sign Up" section
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    TextButton(onClick = onRegisterClick) {
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@AppPreview
@Composable
fun LoginScreenPreview() {
    MomentumTheme {
        LoginScreen(
            state = LoginState(),
            onLogin = { _, _, _ -> },
            onRegisterClick = { },
            onEmailChange = { },
            onPasswordChange = { }
        )
    }
}