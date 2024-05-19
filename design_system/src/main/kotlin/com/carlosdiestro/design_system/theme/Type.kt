package com.carlosdiestro.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.carlosdiestro.design_system.R

internal val pokedexFontFamily = FontFamily(
    Font(resId = R.font.azeret_mono_regular),
    Font(
        resId = R.font.azeret_mono_medium,
        weight = FontWeight.Medium
    )
)

internal val baseline = Typography()

internal val PokedexTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = pokedexFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = pokedexFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = pokedexFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = pokedexFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = pokedexFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = pokedexFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = pokedexFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = pokedexFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = pokedexFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = pokedexFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = pokedexFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = pokedexFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = pokedexFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = pokedexFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = pokedexFontFamily),
)