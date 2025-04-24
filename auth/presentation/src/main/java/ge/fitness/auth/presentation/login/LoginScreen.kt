package ge.fitness.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit
) {
    val context = LocalContext.current
    HandleEvents(viewModel.events) { events ->
        when (events) {
            LoginEvent.LoginSuccess -> {

                onNavigateHome()
            }

            is LoginEvent.ShowError -> {
                Toast.makeText(
                    context,
                    events.message?.let { context.getString(it) },
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onNavigateRegister()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
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
                    text = stringResource(R.string.log_in),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(0.5f))

                Text(
                    text = stringResource(R.string.welcome),
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
                            value = state.email,
                            onValueChange = { onAction(LoginAction.OnEmailChanged(it)) },
                            label = stringResource(R.string.email),
                            placeholder = stringResource(R.string.enter_your_email),
                            isError = state.emailError != null,
                            errorMessage = state.emailError?.let { stringResource(id = it) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        MomentumPasswordTextField(
                            value = state.password,
                            onValueChange = { onAction(LoginAction.OnPasswordChanged(it)) },
                            label = stringResource(R.string.password),
                            placeholder = stringResource(R.string.enter_your_password),
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError?.let { stringResource(id = it) },
                            isPasswordVisible = state.isPasswordVisible,
                            onTogglePasswordVisibility = { onAction(LoginAction.OnTogglePasswordVisibility) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.rememberMe,
                                onCheckedChange = { onAction(LoginAction.OnRememberMeChanged(it)) }
                            )

                            Text(
                                text = stringResource(R.string.remember_me),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = { /* Implement forgot password */ }) {
                                Text(
                                    text = stringResource(R.string.forgot_password),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedMomentumButton(
                    onClick = {
                        onAction(
                            LoginAction.OnLoginClick(
                                email = state.email,
                                password = state.password
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    isEnabled = true,
                    content = {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.log_in),
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.or_sign_up_with),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { /* Implement Google Sign In */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gmail),
                            contentDescription = stringResource(R.string.google_login),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.don_t_have_an_account),
                    )

                    TextButton(onClick = { onAction(LoginAction.OnRegisterClick) }) {
                        Text(
                            text = stringResource(R.string.or_sign_up_with),
                        )
                    }
                }
            }
        }
    }
}

@AppPreview
@Composable
fun LoginScreenPreview() {
    MomentumTheme {
        LoginScreen(
            state = LoginState(
                isLoginEnabled = true
            ),
            onAction = {},
        )
    }
}