package com.forge.os.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 8-point spacing scale. All layout padding, gaps and offsets should resolve
 * to one of these steps so the whole app shares a single spatial rhythm.
 */
object ForgeSpacing {
    val xxs: Dp = 2.dp
    val xs: Dp = 4.dp
    val sm: Dp = 8.dp
    val md: Dp = 12.dp
    val lg: Dp = 16.dp
    val xl: Dp = 20.dp
    val xxl: Dp = 24.dp
    val xxxl: Dp = 32.dp
    val huge: Dp = 40.dp
    val massive: Dp = 48.dp
}

/**
 * Corner-radius scale. A single, restrained set of radii used consistently
 * across chips, cards, sheets and dialogs.
 */
object ForgeRadius {
    /** Small chips, pills, tags. */
    val sm: Dp = 8.dp
    /** Inputs, small cards, list rows. */
    val md: Dp = 12.dp
    /** Standard cards, bubbles. */
    val lg: Dp = 16.dp
    /** Prominent cards, bottom sheets. */
    val xl: Dp = 20.dp
    /** Large sheets / dialogs. */
    val xxl: Dp = 24.dp
    /** Fully round (FABs, avatar, send button). */
    val full: Dp = 999.dp
}

/**
 * Elevation scale (tonal shadow). Used sparingly — the system favours borders
 * and tonal separation over heavy shadows.
 */
object ForgeElevation {
    val none: Dp = 0.dp
    val raised: Dp = 1.dp
    val overlay: Dp = 3.dp
    val floating: Dp = 6.dp
    val modal: Dp = 12.dp
}
