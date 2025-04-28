package ge.fitness.auth.presentation.signup

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ge.fitness.auth.presentation.R
import ge.fitness.core.presentation.design_system.component.AppPreview
import ge.fitness.core.presentation.design_system.component.MomentumCard
import ge.fitness.core.presentation.design_system.component.MomentumPasswordTextField
import ge.fitness.core.presentation.design_system.component.MomentumTextField
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.core.presentation.design_system.icon.GmailIcon
import ge.fitness.core.presentation.design_system.icon.GoBackIcon
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.core.presentation.ui.HandleEvents
import kotlinx.coroutines.launch

@Composable
fun SignUpScreenRoot(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateLogin: () -> Unit,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.state

    val registerSuccessMsg = stringResource(R.string.register_successful)
    val defaultErrorMsg = context.getString(R.string.an_error_occurred)

    var isGoogleFlow by remember { mutableStateOf(false) }
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            isGoogleFlow = true
            viewModel.onAction(SignupAction.ProcessGoogleSignIn(result.data))
        } else {
            scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.google_sign_in_was_canceled))}
        }
    }

    HandleEvents(viewModel.events) { event ->
        when (event) {
            SignUpEvent.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(registerSuccessMsg)
                }
                if (isGoogleFlow) onNavigateHome()
                else onNavigateLogin()
            }
            is SignUpEvent.ShowError -> {
                val errorMsg = event.error
                    ?.let { context.getString(it) }
                    ?: defaultErrorMsg

                scope.launch {
                    snackbarHostState.showSnackbar(errorMsg)
                }
            }
        }
    }

    SignUpScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SignupAction.OnLoginClick -> onNavigateLogin()
                SignupAction.OnGoogleSignInClick -> googleSignInLauncher.launch(
                    viewModel.onAction(SignupAction.GetGoogleSignInIntent) as Intent
                )
                else -> viewModel.onAction(action)
            }
        },
        onBackClick = onBackClick
    )
}

@Composable
fun SignUpScreen(
    state: SignUpState,
    onAction: (SignupAction) -> Unit,
    onBackClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = GoBackIcon,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.let_s_start),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            MomentumCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MomentumTextField(
                        value = state.fullName,
                        onValueChange = { onAction(SignupAction.OnFullNameChanged(it)) },
                        label = stringResource(R.string.full_name),
                        placeholder = stringResource(R.string.enter_your_full_name),
                        isError = state.fullNameError != null,
                        errorMessage = state.fullNameError?.let { stringResource(id = it) }
                    )

                    MomentumTextField(
                        value = state.email,
                        onValueChange = { onAction(SignupAction.OnEmailChanged(it)) },
                        label = stringResource(R.string.email),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        placeholder = stringResource(R.string.enter_your_email),
                        isError = state.emailError != null,
                        errorMessage = state.emailError?.let { stringResource(id = it) }
                    )

                    MomentumPasswordTextField(
                        value = state.password,
                        onValueChange = { onAction(SignupAction.OnPasswordChanged(it)) },
                        label = stringResource(R.string.password),
                        isPasswordVisible = state.isPasswordVisible,
                        placeholder = stringResource(R.string.enter_your_password),
                        isError = state.passwordError != null,
                        errorMessage = state.passwordError?.let { stringResource(id = it) },
                        onTogglePasswordVisibility = {
                            onAction(SignupAction.OnTogglePasswordVisibility)
                        }
                    )

                    MomentumPasswordTextField(
                        value = state.confirmPassword,
                        onValueChange = { onAction(SignupAction.OnRepeatPasswordChanged(it)) },
                        label = stringResource(R.string.confirm_password),
                        isPasswordVisible = state.isConfirmPasswordVisible,
                        placeholder = stringResource(R.string.confirm_your_password),
                        isError = state.confirmPasswordError != null,
                        errorMessage = state.confirmPasswordError?.let { stringResource(id = it) },
                        onTogglePasswordVisibility = {
                            onAction(SignupAction.OnToggleConfirmPasswordVisibility)
                        }
                    )
                }
            }

            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.by_continuing_you_agree_to))
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.terms_of_use))
                    }
                    append(stringResource(R.string.and))
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.privacy_policy))
                    }
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

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
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .testTag("signupProgressIndicator"),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = stringResource(id = R.string.sign_up))
                    }
                },
                isEnabled = (!state.isLoading &&
                        state.fullNameError == null &&
                        state.emailError == null &&
                        state.passwordError == null &&
                        state.confirmPasswordError == null &&
                        state.fullName.isNotBlank() &&
                        state.email.isNotBlank() &&
                        state.password.isNotBlank() &&
                        state.confirmPassword.isNotBlank() &&
                        state.password == state.confirmPassword)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.or_sign_up_with),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { onAction(SignupAction.OnGoogleSignInClick) }
                        .testTag("googleSignInButton"),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isGoogleSignInLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    } else {
                        Image(
                            imageVector = GmailIcon,
                            contentDescription = stringResource(id = R.string.google_login),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextButton(
                    onClick = { onAction(SignupAction.OnLoginClick) },
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    modifier = Modifier.testTag("loginNavButton")
                ) {
                    Text(
                        text = stringResource(id = R.string.log_in),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
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
            ),
            onAction = {}
        ) {

        }
    }
}