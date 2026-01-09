package com.valhalla.freyr.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NorseColorScheme = darkColorScheme(
    primary = NorseGold,
    onPrimary = NorseBlack,
    primaryContainer = NorseGoldVariant,
    onPrimaryContainer = NorseWhite,
    secondary = NorseGold,
    onSecondary = NorseBlack,
    background = NorseBlack,
    onBackground = NorseWhite,
    surface = NorseSurface,
    onSurface = NorseWhite,
    surfaceVariant = NorseDarkGrey,
    onSurfaceVariant = NorseWhite,
    error = NorseError,
    onError = NorseBlack
)

@Composable
fun FreyrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We strictly use the Norse Dark Theme for this app as per requirements
    val colorScheme = NorseColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
