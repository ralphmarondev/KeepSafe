package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeState
import com.ralphmarondev.keepsafe.navigation.AppNavigation

@Composable
fun App(themeState: ThemeState) {
    CompositionLocalProvider(LocalThemeState provides themeState) {
        KeepSafeTheme(
            darkTheme = themeState.darkTheme.collectAsState().value
        ) {
            AppNavigation()
        }
    }
}