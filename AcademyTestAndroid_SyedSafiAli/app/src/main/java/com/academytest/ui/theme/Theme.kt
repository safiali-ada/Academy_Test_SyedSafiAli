package com.academytest.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

/**
 * iOS-styled Material 3 color scheme.
 * Maps iOS system colors onto Material3 roles so that standard
 * Material3 components (TopAppBar, Surface, etc.) look iOS-like.
 */
private val LightColorScheme = lightColorScheme(
    primary = IOSBlue,
    onPrimary = Color.White,
    secondary = IOSGray,
    onSecondary = Color.White,
    background = IOSLightBackground,
    onBackground = Color.Black,
    surface = IOSLightSurface,
    onSurface = Color.Black,
    surfaceVariant = IOSLightSurface,
    onSurfaceVariant = IOSGray,
    outline = IOSLightSeparator,
    outlineVariant = IOSGray5,
    error = IOSRed,
    onError = Color.White,
    surfaceContainerHighest = IOSLightSurface,
    surfaceContainer = IOSLightSurface,
    surfaceContainerLow = IOSLightBackground,
)

private val DarkColorScheme = darkColorScheme(
    primary = IOSBlue,
    onPrimary = Color.White,
    secondary = IOSGray,
    onSecondary = Color.White,
    background = IOSDarkBackground,
    onBackground = Color.White,
    surface = IOSDarkSurface,
    onSurface = Color.White,
    surfaceVariant = IOSDarkSurface,
    onSurfaceVariant = IOSGray2,
    outline = IOSDarkSeparator,
    outlineVariant = IOSDarkSeparator,
    error = IOSRed,
    onError = Color.White,
    surfaceContainerHighest = IOSDarkElevatedSurface,
    surfaceContainer = IOSDarkSurface,
    surfaceContainerLow = IOSDarkBackground,
)

/**
 * Typography inspired by iOS San Francisco sizing.
 */
private val IOSTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.37.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 13.sp,
    ),
)

@Composable
fun AcademyTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? Activity ?: return@SideEffect
            activity.window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(activity.window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = IOSTypography,
        content = content,
    )
}
