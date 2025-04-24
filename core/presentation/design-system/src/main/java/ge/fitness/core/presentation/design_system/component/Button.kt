package ge.fitness.core.presentation.design_system.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ge.fitness.core.presentation.design_system.theme.MomentumTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
annotation class AppPreview

/**
 * Primary filled button with guaranteed text visibility in all themes
 */
@Composable
fun MomentumButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPaddingValues: PaddingValues = ButtonDefaults.ContentPadding,
    isEnabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,  // White in dark mode, black in light mode
            contentColor = MaterialTheme.colorScheme.background,      // Black in dark mode, white in light mode
            disabledContainerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f)
        ),
        contentPadding = contentPaddingValues,
        content = content
    )
}

/**
 * Outlined button with guaranteed text visibility in all themes
 */
@Composable
fun OutlinedMomentumButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPaddingValues: PaddingValues = ButtonDefaults.ContentPadding,
    isEnabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    // Change from Button to OutlinedButton
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,  // Transparent background
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        contentPadding = contentPaddingValues,
        content = content
    )
}

@AppPreview
@Composable
private fun MomentumButtonPreview() {
    MomentumTheme {
        Surface {
            MomentumButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text("Lukaadadada")
                }
            )
        }
    }
}

@AppPreview
@Composable
private fun OutlinedMomentumButtonPreview() {
    MomentumTheme {
        Surface {
            OutlinedMomentumButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text("Login")
                }
            )
        }
    }
}