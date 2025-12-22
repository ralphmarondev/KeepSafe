package com.ralphmarondev.keepsafe.core.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalThemeState = staticCompositionLocalOf<ThemeState> {
    error("ThemeState is not provided.")
}