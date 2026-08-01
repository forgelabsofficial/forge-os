package com.forge.os.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Forge Design System — core color primitives.
 *
 * The system is built around a deep, neutral "graphite" ramp (not pure black)
 * and a single restrained ember accent. Semantic colors carry meaning; the
 * accent is reserved for primary actions and focus. Everything is tuned for
 * WCAG-AA contrast on its intended surface.
 *
 * These primitives feed [ForgePalette] (the per-theme CompositionLocal) and
 * the Material [androidx.compose.material3.ColorScheme]. Screens should read
 * colors from the palette, not from these constants directly.
 */

// ── Brand ────────────────────────────────────────────────────────────────────
/** Primary accent — a refined ember/copper. Softer than the legacy #FF4500 so
 *  it reads as premium on dark surfaces and passes contrast on light ones. */
val ForgeEmber = Color(0xFFFF6B3D)
/** Pressed / hover variant of the accent. */
val ForgeEmberPressed = Color(0xFFE85A2E)
/** Secondary cool accent used sparingly for informational highlights. */
val ForgeSky = Color(0xFF5E9BFF)

// ── Dark theme "graphite" ramp ───────────────────────────────────────────────
val Graphite900 = Color(0xFF0E0F12)   // app background
val Graphite850 = Color(0xFF14161A)   // sunken / inset
val Graphite800 = Color(0xFF181B20)   // card surface
val Graphite750 = Color(0xFF1F232B)   // elevated surface / hover
val Graphite700 = Color(0xFF262B34)   // highest elevation
val GraphiteBorder = Color(0xFF2A2F39) // hairline border on dark
val GraphiteBorderSoft = Color(0xFF22262E)

// ── Dark theme text ──────────────────────────────────────────────────────────
val InkHigh = Color(0xFFF3F5F8)    // primary text  (~15:1 on Graphite900)
val InkMedium = Color(0xFFA7AEBB)  // secondary text (~7:1)
val InkLow = Color(0xFF6B7280)     // tertiary / captions (~4.6:1)

// ── Semantic (dark) ──────────────────────────────────────────────────────────
val SuccessDark = Color(0xFF3ECF8E)
val SuccessDarkBg = Color(0xFF0C2A1E)
val WarningDark = Color(0xFFF5B84C)
val WarningDarkBg = Color(0xFF2E2210)
val DangerDark = Color(0xFFF26D6D)
val DangerDarkBg = Color(0xFF2E1414)
val InfoDark = Color(0xFF7EA6FF)
val InfoDarkBg = Color(0xFF16233E)

// ── Light theme ramp ─────────────────────────────────────────────────────────
val Paper50 = Color(0xFFFBFBFC)    // app background
val Paper100 = Color(0xFFF4F4F6)   // sunken
val Paper0 = Color(0xFFFFFFFF)     // card surface
val PaperElev = Color(0xFFFFFFFF)  // elevated
val PaperBorder = Color(0xFFE4E6EA)
val PaperBorderSoft = Color(0xFFECEEF1)

// ── Light theme text ─────────────────────────────────────────────────────────
val InkLightHigh = Color(0xFF14171C)
val InkLightMedium = Color(0xFF4B5563)
val InkLightLow = Color(0xFF8A919C)

// ── Semantic (light) ─────────────────────────────────────────────────────────
val SuccessLight = Color(0xFF1B9E6B)
val SuccessLightBg = Color(0xFFDFF5EA)
val WarningLight = Color(0xFFB9791A)
val WarningLightBg = Color(0xFFFBEFD8)
val DangerLight = Color(0xFFD24444)
val DangerLightBg = Color(0xFFFBE4E4)
val InfoLight = Color(0xFF2F6BE0)
val InfoLightBg = Color(0xFFE3EDFC)

// ── Legacy aliases (kept so any straggler references still resolve) ──────────
val ForgeOrange = ForgeEmber
val ForgeBlue = ForgeSky
val ForgeGreen = SuccessDark
val ForgeSurface = Graphite800
val ForgeSurfaceVariant = Graphite750
val ForgeOnSurface = InkHigh
val ForgeOnSurfaceVariant = InkMedium
val ForgeError = DangerDark
val ForgeWarning = WarningDark

val ForgeLightSurface = Paper50
val ForgeLightSurfaceVariant = Paper0
val ForgeLightOnSurface = InkLightHigh
val ForgeLightOnSurfaceVariant = InkLightMedium

// Phase S / UI refactor (legacy)
val Glass = Color(0xFFFFFFFF)
val VibrantBlue = ForgeSky
