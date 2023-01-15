package com.example.mlkitplayground.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF0d47a1),
    primaryVariant = Color(0xFF5472d3),
    secondary = Color(0xFF303f9f),
    secondaryVariant = Color(0xFF001970),
    background = Color(0xFF0d47a1),
    surface = Color(0xFF0d47a1),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF0d47a1),
    primaryVariant = Color(0xFF5472d3),
    secondary = Color(0xFF303f9f),
    secondaryVariant = Color(0xFF001970),
    background = Color(0xFF0d47a1),
    surface = Color(0xFF0d47a1),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MLKitPlaygroundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}