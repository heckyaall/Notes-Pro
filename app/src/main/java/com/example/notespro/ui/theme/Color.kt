package com.example.notespro.ui.theme

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.darkColors
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.lightColors
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


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
