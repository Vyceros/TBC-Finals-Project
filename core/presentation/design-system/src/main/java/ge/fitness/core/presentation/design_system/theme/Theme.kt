package ge.fitness.core.presentation.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MomentumPurple,
    onPrimary = MomentumWhite,
    primaryContainer = MomentumLightPurple,
    onPrimaryContainer = MomentumBlack,

    secondary = MomentumLimeGreen,
    onSecondary = MomentumBlack,
    secondaryContainer = MomentumLimeGreen,
    onSecondaryContainer = MomentumBlack,

    background = MomentumBlack,
    onBackground = MomentumWhite,

    surface = MomentumBlack,
    onSurface = MomentumWhite,

    error = MomentumRed,
    onError = MomentumWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = MomentumPurple,
    onPrimary = MomentumWhite,
    primaryContainer = MomentumLightPurple,
    onPrimaryContainer = MomentumBlack,

    secondary = MomentumLimeGreen,
    onSecondary = MomentumBlack,
    secondaryContainer = MomentumLimeGreen,
    onSecondaryContainer = MomentumBlack,

    background = MomentumWhite,
    onBackground = MomentumBlack,

    surface = MomentumWhite,
    onSurface = MomentumBlack,

    error = MomentumRed,
    onError = MomentumWhite,
)

@Composable
fun MomentumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MomentumTypography,
        content = content
    )
}
