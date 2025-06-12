package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.ralphmarondev.keepsafe.core.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
    themeState: ThemeState
) {
    CompositionLocalProvider(
        LocalThemeState provides themeState
    ) {
        KeepSafeTheme(
            darkTheme = themeState.darkMode.collectAsState().value
        ) {
            AppNavigation()
        }
    }
}