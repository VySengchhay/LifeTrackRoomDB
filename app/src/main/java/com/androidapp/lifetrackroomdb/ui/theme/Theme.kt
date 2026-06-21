package com.example.lifetrackpro.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.androidapp.lifetrackroomdb.ui.theme.typography

private val DarkColorScheme = darkColorScheme(
    primary = LifeTrackPrimaryLight,
    onPrimary = LifeTrackOnPrimary,
    primaryContainer = LifeTrackPrimaryDark,
    onPrimaryContainer = LifeTrackPrimaryContainer,

    secondary = LifeTrackSecondaryLight,
    onSecondary = LifeTrackOnSecondary,
    secondaryContainer = LifeTrackSecondaryDark,
    onSecondaryContainer = LifeTrackSecondaryContainer,

    tertiary = LifeTrackAccentCyan,
    onTertiary = White,
    tertiaryContainer = LifeTrackSurfaceVariantDark,
    onTertiaryContainer = LifeTrackTextPrimaryDark,

    background = LifeTrackBackgroundDark,
    onBackground = LifeTrackTextPrimaryDark,

    surface = LifeTrackSurfaceDark,
    onSurface = LifeTrackTextPrimaryDark,

    surfaceVariant = LifeTrackSurfaceVariantDark,
    onSurfaceVariant = LifeTrackTextSecondaryDark,

    error = StatusError,
    onError = White,
    errorContainer = PriorityUrgent,
    onErrorContainer = StatusErrorContainer,

    outline = LifeTrackBorderDark,
    outlineVariant = LifeTrackDividerDark,

    inverseSurface = LifeTrackSurface,
    inverseOnSurface = LifeTrackTextPrimary,
    inversePrimary = LifeTrackPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = LifeTrackPrimary,
    onPrimary = LifeTrackOnPrimary,
    primaryContainer = LifeTrackPrimaryContainer,
    onPrimaryContainer = LifeTrackPrimaryDark,

    secondary = LifeTrackSecondary,
    onSecondary = LifeTrackOnSecondary,
    secondaryContainer = LifeTrackSecondaryContainer,
    onSecondaryContainer = LifeTrackSecondaryDark,

    tertiary = LifeTrackAccentCyan,
    onTertiary = White,
    tertiaryContainer = StatusInfoContainer,
    onTertiaryContainer = LifeTrackPrimaryDark,

    background = LifeTrackBackground,
    onBackground = LifeTrackTextPrimary,

    surface = LifeTrackSurface,
    onSurface = LifeTrackTextPrimary,

    surfaceVariant = LifeTrackSurfaceVariant,
    onSurfaceVariant = LifeTrackTextSecondary,

    error = StatusError,
    onError = White,
    errorContainer = StatusErrorContainer,
    onErrorContainer = PriorityUrgent,

    outline = LifeTrackBorder,
    outlineVariant = LifeTrackDivider,

    inverseSurface = LifeTrackSurfaceDark,
    inverseOnSurface = LifeTrackTextPrimaryDark,
    inversePrimary = LifeTrackPrimaryLight
)

@Composable
fun LifeTrackProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme

        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}