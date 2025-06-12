package com.ralphmarondev.keepsafe.core.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalThemeState = staticCompositionLocalOf<ThemeState> {
    error("ThemeState is not provided.")
}