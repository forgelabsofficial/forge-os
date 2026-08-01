package com.forge.os.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.forge.os.domain.heartbeat.HealthLevel
import com.forge.os.presentation.components.*
import com.forge.os.presentation.theme.forgePalette

/**
 * Modern system status dashboard — Quiet Power design.
 * Clean list-row layout with semantic health indicators.
 */
@Composable
fun ModernStatusScreen(
    onNavigateBack: () -> Unit,
    viewModel: StatusViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ModernBg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SimpleHeader(
                title = "System Status",
                subtitle = "Last check: ${formatTime(status.timestamp)}",
                onBackClick = onNavigateBack
            ) {
                HealthStatusBadge(status.overallHealth)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Alerts
                if (status.alerts.isNotEmpty()) {
                    item {
                        AlertsSection(alerts = status.alerts)
                    }
                }

                // Overall health
                item {
                    OverallHealthCard(
                        health = status.overallHealth,
                        onRefresh = { viewModel.refresh() }
                    )
                }

                // Components
                item {
                    SectionHeader(title = "COMPONENTS")
                }

                items(status.components.entries.toList()) { (name, component) ->
                    ComponentRow(
                        name = name,
                        health = HealthLevel.valueOf(component.health),
                        metrics = component.metrics,
                        message = component.message
                    )
                }

                // Recommendations
                if (status.recommendations.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(4.dp))
                        SectionHeader(title = "RECOMMENDATIONS")
                    }
                    item {
                        RecommendationsCard(recommendations = status.recommendations)
                    }
                }

                // Refresh
                item {
                    Spacer(Modifier.height(4.dp))
                    ModernButton(
                        text = "Refresh Status",
                        onClick = { viewModel.refresh() },
                        icon = Icons.Outlined.Refresh,
                        variant = ButtonVariant.Outline,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ── Health Badge ────────────────────────────────────────────────────────────

@Composable
private fun HealthStatusBadge(health: HealthLevel) {
    val (color, text) = getHealthColorAndText(health)

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = if (health == HealthLevel.HEALTHY) 1f else 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Surface(
        color = color.copy(alpha = 0.12f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = alpha))
            )
            Text(
                text,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun getHealthColorAndText(health: HealthLevel): Pair<Color, String> {
    return when (health) {
        HealthLevel.HEALTHY -> forgePalette.success to "Healthy"
        HealthLevel.WARNING -> forgePalette.warning to "Warning"
        HealthLevel.CRITICAL -> forgePalette.danger to "Critical"
        HealthLevel.DOWN -> forgePalette.textMuted to "Down"
    }
}

// ── Alerts ──────────────────────────────────────────────────────────────────

@Composable
private fun AlertsSection(alerts: List<com.forge.os.domain.heartbeat.AlertInfo>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        alerts.forEach { alert ->
            Surface(
                color = forgePalette.warning.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, forgePalette.warning.copy(alpha = 0.2f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = forgePalette.warning,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        alert.message,
                        color = ModernTextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// ── Overall Health Card ─────────────────────────────────────────────────────

@Composable
private fun OverallHealthCard(
    health: HealthLevel,
    onRefresh: () -> Unit
) {
    val healthColor = when (health) {
        HealthLevel.HEALTHY -> forgePalette.success
        HealthLevel.WARNING -> forgePalette.warning
        HealthLevel.CRITICAL -> forgePalette.danger
        HealthLevel.DOWN -> forgePalette.textMuted
    }

    val healthFraction = when (health) {
        HealthLevel.HEALTHY -> 1f
        HealthLevel.WARNING -> 0.6f
        HealthLevel.CRITICAL -> 0.3f
        HealthLevel.DOWN -> 0f
    }

    val animatedFraction by animateFloatAsState(
        targetValue = healthFraction,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "health_bar"
    )

    ModernCard {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "Overall Health",
                        color = ModernTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(healthColor)
                        )
                        Text(
                            when (health) {
                                HealthLevel.HEALTHY -> "All Systems Operational"
                                HealthLevel.WARNING -> "Some Issues Detected"
                                HealthLevel.CRITICAL -> "Critical Issues"
                                HealthLevel.DOWN -> "System Down"
                            },
                            color = ModernTextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier
                        .size(36.dp)
                        .background(ModernAccent.copy(alpha = 0.12f), CircleShape)
                ) {
                    Icon(
                        Icons.Outlined.Refresh,
                        contentDescription = "Refresh",
                        tint = ModernAccent,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Health progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(forgePalette.borderSoft)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedFraction)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(healthColor)
                )
            }
        }
    }
}

// ── Component Row ───────────────────────────────────────────────────────────

@Composable
private fun ComponentRow(
    name: String,
    health: HealthLevel,
    metrics: Map<String, String>,
    message: String?
) {
    val healthColor = when (health) {
        HealthLevel.HEALTHY -> forgePalette.success
        HealthLevel.WARNING -> forgePalette.warning
        HealthLevel.CRITICAL -> forgePalette.danger
        HealthLevel.DOWN -> forgePalette.textMuted
    }

    ModernCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header row: dot + name + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(healthColor)
                    )
                    Text(
                        name.replaceFirstChar { it.uppercase() },
                        color = ModernTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                StatusBadge(
                    status = health.name.lowercase().replaceFirstChar { it.uppercase() },
                    color = healthColor
                )
            }

            // Metrics
            if (metrics.isNotEmpty()) {
                HorizontalDivider(color = forgePalette.divider, thickness = 0.5.dp)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    metrics.forEach { (key, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                key,
                                color = ModernTextSecondary,
                                fontSize = 12.sp
                            )
                            Text(
                                value,
                                color = ModernTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Message
            if (message != null) {
                Surface(
                    color = forgePalette.warning.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        message,
                        color = forgePalette.warning,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

// ── Recommendations ─────────────────────────────────────────────────────────

@Composable
private fun RecommendationsCard(recommendations: List<String>) {
    ModernCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Lightbulb,
                    contentDescription = null,
                    tint = ModernAccent,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    "Recommendations",
                    color = ModernTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                recommendations.forEach { recommendation ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .offset(y = 6.dp)
                                .background(ModernAccent, CircleShape)
                        )
                        Text(
                            recommendation,
                            color = ModernTextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// ── Helpers ─────────────────────────────────────────────────────────────────

private fun formatTime(ts: Long): String {
    val fmt = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.US)
    return fmt.format(java.util.Date(ts))
}
