package com.example.notespro.ui.theme

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.darkColors
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.lightColors
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Color


// Define custom colors using hexadecimal values
val LightBlue = Color(0xFFADD8E6) // Light Blue
val DarkBlue = Color(0xFF00008B) // Dark Blue
val Cyan = Color(0xFF00FFFF) // Cyan


// Light theme colors
val LightColors = lightColors(
    primary = Color.Blue,
    primaryVariant = Color(DarkBlue.value),
    secondary = Color.Cyan
)

// Dark theme colors
val DarkColors = darkColors(
    primary = Color(LightBlue.value),
    primaryVariant = Color.Cyan,
    secondary = Color.Blue
)
