package com.forge.os.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ForgeEmber,
    onPrimary = ForgeDarkPalette.onAccent,
    primaryContainer = ForgeEmber.copy(alpha = 0.16f),
    onPrimaryContainer = InkHigh,
    secondary = ForgeSky,
    onSecondary = InkHigh,
    secondaryContainer = Graphite750,
    onSecondaryContainer = InkHigh,
    tertiary = SuccessDark,
    onTertiary = Graphite900,
    background = Graphite900,
    onBackground = InkHigh,
    surface = Graphite800,
    onSurface = InkHigh,
    surfaceVariant = Graphite750,
    onSurfaceVariant = InkMedium,
    surfaceContainerLowest = Graphite850,
    surfaceContainer = Graphite800,
    surfaceContainerHigh = Graphite750,
    surfaceContainerHighest = Graphite700,
    outline = GraphiteBorder,
    outlineVariant = GraphiteBorderSoft,
    error = DangerDark,
    onError = Graphite900,
    errorContainer = DangerDarkBg,
    onErrorContainer = DangerDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ForgeEmberPressed,
    onPrimary = ForgeLightPalette.onAccent,
    primaryContainer = ForgeEmberPressed.copy(alpha = 0.12f),
    onPrimaryContainer = InkLightHigh,
    secondary = InfoLight,
    onSecondary = Paper0,
    secondaryContainer = Paper100,
    onSecondaryContainer = InkLightHigh,
    tertiary = SuccessLight,
    onTertiary = Paper0,
    background = Paper50,
    onBackground = InkLightHigh,
    surface = Paper0,
    onSurface = InkLightHigh,
    surfaceVariant = Paper100,
    onSurfaceVariant = InkLightMedium,
    surfaceContainerLowest = Paper100,
    surfaceContainer = Paper0,
    surfaceContainerHigh = Paper0,
    surfaceContainerHighest = Paper100,
    outline = PaperBorder,
    outlineVariant = PaperBorderSoft,
    error = DangerLight,
    onError = Paper0,
    errorContainer = DangerLightBg,
    onErrorContainer = DangerLight,
)

/** App-wide shape scale mapped from [ForgeRadius]. */
private val ForgeShapes = Shapes(
    small = RoundedCornerShape(ForgeRadius.sm),
    medium = RoundedCornerShape(ForgeRadius.md),
    large = RoundedCornerShape(ForgeRadius.lg),
    extraLarge = RoundedCornerShape(ForgeRadius.xl),
)

@Composable
fun ForgeTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val palette = if (darkTheme) ForgeDarkPalette else ForgeLightPalette
    CompositionLocalProvider(LocalForgePalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ForgeTypography,
            shapes = ForgeShapes,
            content = content
        )
    }
}
