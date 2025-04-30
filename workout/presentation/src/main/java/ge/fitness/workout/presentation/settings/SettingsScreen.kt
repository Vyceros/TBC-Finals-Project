package ge.fitness.workout.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ge.fitness.core.presentation.design_system.component.OutlinedMomentumButton
import ge.fitness.workout.presentation.R

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    SettingsScreen(
        onAction = {
            viewModel.onAction(it)
            when (it) {
                is SettingsAction.Logout -> {
                    onLogout()
                }
            }
        },
    )
}

@Composable
fun SettingsScreen(
    onAction: (SettingsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.thank_you_for_using_our_app_more_features_will_come),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedMomentumButton(onClick = { onAction(SettingsAction.Logout) }) {
            Text(stringResource(R.string.logout))
        }
    }
}



