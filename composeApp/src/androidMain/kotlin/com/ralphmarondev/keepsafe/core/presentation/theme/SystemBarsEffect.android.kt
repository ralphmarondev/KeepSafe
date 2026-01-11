package com.ralphmarondev.keepsafe.core.presentation.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SystemBarsEffect() {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val colorScheme = MaterialTheme.colorScheme

    SideEffect {
        val controller =
            WindowCompat.getInsetsController(window, view)

        val lightIcons = colorScheme.surface.luminance() > 0.5f

        controller.isAppearanceLightStatusBars = lightIcons
        controller.isAppearanceLightNavigationBars = lightIcons
    }
}