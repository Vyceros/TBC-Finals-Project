package ge.fitness.auth.presentation.login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ge.fitness.auth.presentation.R
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.MomentumCard
import ge.fitness.core.presentation.design_system.component.MomentumPasswordTextField
import ge.fitness.core.presentation.design_system.component.MomentumTextField
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.core.presentation.design_system.icon.GmailIcon
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.core.presentation.ui.HandleEvents
import kotlinx.coroutines.launch

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK ->
                viewModel.onAction(LoginAction.ProcessGoogleSignIn(result.data))
            Activity.RESULT_CANCELED -> scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.google_sign_in_was_canceled))
            }
            else -> scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.google_sign_in_failed_please_try_again))
            }
        }
    }

    HandleEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.LoginSuccess -> {
                scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.signed_in_successfully)) }
                onNavigateHome()
            }
            is LoginEvent.ShowError -> {
                val msg = event.message
                    ?.let { context.getString(it) }
                    ?: context.getString(R.string.authentication_failed_please_try_again)
                scope.launch { snackbarHostState.showSnackbar(msg) }
            }
        }
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onNavigateRegister()
                LoginAction.OnGoogleSignInClick -> googleSignInLauncher.launch(
                    viewModel.onAction(LoginAction.GetGoogleSignInIntent) as Intent
                )
                else -> viewModel.onAction(action)
            }
        }
    )
}


@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

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
                text = stringResource(R.string.auth_title_text),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            MomentumCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .padding(vertical = 16.dp),
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        placeholder = stringResource(R.string.enter_your_email),
                        isError = state.emailError != null,
                        errorMessage = state.emailError?.let { stringResource(id = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("emailField"),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("passwordField"),
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
                                color = MaterialTheme.colorScheme.onBackground
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
                    .height(56.dp)
                    .testTag("loginButton"),
                isEnabled = state.isLoginEnabled,
                content = {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .testTag("loginProgressIndicator")
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
                        .clickable { onAction(LoginAction.OnGoogleSignInClick) }
                        .testTag("googleSignInButton"),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isGoogleSignInLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .testTag("googleSignInProgressIndicator"),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Image(
                            imageVector = GmailIcon,
                            contentDescription = stringResource(R.string.google_login),
                            modifier = Modifier.size(24.dp)
                        )
                    }
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
                        text = stringResource(R.string.sign_up),
                    )
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