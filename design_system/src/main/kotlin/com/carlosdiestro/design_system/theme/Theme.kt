package com.carlosdiestro.design_system.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.carlosdiestro.design_system.theme.SchemeColorContrast.High
import com.carlosdiestro.design_system.theme.SchemeColorContrast.Medium
import com.carlosdiestro.design_system.theme.SchemeColorContrast.Standard

@Composable
fun PokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    schemeColorContrast: SchemeColorContrast = Standard,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme                                                      -> {
            when (schemeColorContrast) {
                Standard -> darkScheme
                Medium   -> mediumContrastDarkColorScheme
                High     -> highContrastDarkColorScheme
            }
        }

        else                                                           -> {
            when (schemeColorContrast) {
                Standard -> lightScheme
                Medium   -> mediumContrastLightColorScheme
                High     -> highContrastLightColorScheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PokedexTypography,
        content = content
    )
}

enum class SchemeColorContrast {
    Standard,
    Medium,
    High
}