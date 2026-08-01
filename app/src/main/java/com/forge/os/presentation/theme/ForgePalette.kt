package com.forge.os.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Per-theme palette for the unified Forge Design System.
 *
 * Every screen reads colors through [forgePalette] (backed by
 * [LocalForgePalette]), so flipping the theme switcher repaints the whole app.
 * The field names are stable — the original set is preserved so all existing
 * module screens keep compiling — while the values now resolve to the refined
 * graphite/ember system. New semantic fields (with defaults) expose elevation,
 * warning and "on-accent" roles that the redesigned components rely on.
 */
data class ForgePalette(
    /** Primary brand accent. */
    val orange: Color,
    /** App background. */
    val bg: Color,
    /** Default card / sheet surface. */
    val surface: Color,
    /** Elevated / hover surface. */
    val surface2: Color,
    /** Hairline border. */
    val border: Color,
    /** Primary text. */
    val textPrimary: Color,
    /** Secondary text. */
    val textMuted: Color,
    /** Tertiary / caption text. */
    val textDim: Color,
    val success: Color,
    val successBg: Color,
    val danger: Color,
    val dangerBg: Color,
    val info: Color,
    /** Accent used for ambient "pulse" moments (kept for compatibility). */
    val neuralPulse: Color,
    /** Accent used for "thinking" / in-progress (kept for compatibility). */
    val thinking: Color,

    // ── New semantic roles (defaults keep old call-sites source-compatible) ──
    /** Sunken / inset surface (input wells, code blocks). */
    val surfaceSunken: Color = surface2,
    /** Highest-elevation surface (menus, dialogs). */
    val surfaceElevated: Color = surface2,
    /** Softer border for subtle separators. */
    val borderSoft: Color = border,
    /** Accent in its pressed/hover state. */
    val accentPressed: Color = orange,
    /** Content drawn on top of [orange] (e.g. filled primary button label). */
    val onAccent: Color = Color(0xFFFFFFFF),
    /** Translucent accent wash for selected/highlighted backgrounds. */
    val accentSoft: Color = orange.copy(alpha = 0.14f),
    val warning: Color = thinking,
    val warningBg: Color = surface2,
    val infoBg: Color = surface2,
    /** Semi-transparent glass overlay for floating elements (composer, sheets). */
    val surfaceGlass: Color = surface.copy(alpha = 0.82f),
    /** Softer-than-border divider for subtle separators. */
    val divider: Color = border.copy(alpha = 0.5f),
)

val ForgeDarkPalette = ForgePalette(
    orange = ForgeEmber,
    bg = Graphite900,
    surface = Graphite800,
    surface2 = Graphite750,
    border = GraphiteBorder,
    textPrimary = InkHigh,
    textMuted = InkMedium,
    textDim = InkLow,
    success = SuccessDark,
    successBg = SuccessDarkBg,
    danger = DangerDark,
    dangerBg = DangerDarkBg,
    info = InfoDark,
    neuralPulse = ForgeEmber,
    thinking = WarningDark,
    surfaceSunken = Graphite850,
    surfaceElevated = Graphite700,
    borderSoft = GraphiteBorderSoft,
    accentPressed = ForgeEmberPressed,
    onAccent = Color(0xFF1A0D08),
    accentSoft = ForgeEmber.copy(alpha = 0.14f),
    warning = WarningDark,
    warningBg = WarningDarkBg,
    infoBg = InfoDarkBg,
    surfaceGlass = Graphite800.copy(alpha = 0.82f),
    divider = GraphiteBorder.copy(alpha = 0.5f),
)

val ForgeLightPalette = ForgePalette(
    orange = ForgeEmberPressed,
    bg = Paper50,
    surface = Paper0,
    surface2 = Paper100,
    border = PaperBorder,
    textPrimary = InkLightHigh,
    textMuted = InkLightMedium,
    textDim = InkLightLow,
    success = SuccessLight,
    successBg = SuccessLightBg,
    danger = DangerLight,
    dangerBg = DangerLightBg,
    info = InfoLight,
    neuralPulse = ForgeEmberPressed,
    thinking = WarningLight,
    surfaceSunken = Paper100,
    surfaceElevated = Paper0,
    borderSoft = PaperBorderSoft,
    accentPressed = ForgeEmber,
    onAccent = Color(0xFFFFFFFF),
    accentSoft = ForgeEmberPressed.copy(alpha = 0.12f),
    warning = WarningLight,
    warningBg = WarningLightBg,
    infoBg = InfoLightBg,
    surfaceGlass = Paper0.copy(alpha = 0.85f),
    divider = PaperBorder.copy(alpha = 0.5f),
)

/**
 * CompositionLocal that screens read to obtain the active palette. Defaults
 * to the dark palette so non-themed previews still render sensibly.
 */
val LocalForgePalette = staticCompositionLocalOf { ForgeDarkPalette }

/** Convenience accessor for use inside @Composable functions. */
val forgePalette: ForgePalette
    @Composable
    @ReadOnlyComposable
    get() = LocalForgePalette.current
