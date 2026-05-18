package com.kalavidara.balaga.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val KalavidaraColorScheme = lightColorScheme(
    primary         = DeepRed,
    onPrimary       = WarmWhite,
    primaryContainer= DarkRed,
    secondary       = Forest,
    onSecondary     = WarmWhite,
    tertiary        = Gold,
    background      = WarmWhite,
    surface         = CardBg,
    onBackground    = Ink,
    onSurface       = Ink,
    outline         = BorderColor,
)

@Composable
fun KalavidaraBalagaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KalavidaraColorScheme,
        typography  = AppTypography,
        content     = content
    )
}