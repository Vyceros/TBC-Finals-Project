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
    onPrimaryContainer = MomentumDarkGray,

    secondary = MomentumLimeGreen,
    onSecondary = MomentumDarkGray,
    secondaryContainer = MomentumLimeGreen,
    onSecondaryContainer = MomentumDarkGray,

    background = MomentumDarkGray,
    onBackground = MomentumWhite,

    surface = MomentumDarkGray,
    onSurface = MomentumWhite,

    error = MomentumRed,
    onError = MomentumWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = MomentumPurple,
    onPrimary = MomentumBlack,
    primaryContainer = MomentumLightPurple,
    onPrimaryContainer = MomentumDarkGray,

    secondary = MomentumLimeGreen,
    onSecondary = MomentumDarkGray,
    secondaryContainer = MomentumLimeGreen,
    onSecondaryContainer = MomentumDarkGray,

    background = MomentumWhite,
    onBackground = MomentumDarkGray,

    surface = MomentumWhite,
    onSurface = MomentumDarkGray,

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
