package com.ralphmarondev.keepsafe

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.presentation.components.Splash
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeProvider
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(preferences: AppPreferences) {
    ThemeProvider(preferences = preferences) {
        val themeState = LocalThemeState.current

        KeepSafeTheme(
            darkTheme = themeState.darkTheme.value
        ) {
            var startDestination by remember { mutableStateOf<Routes?>(null) }

            LaunchedEffect(Unit) {
                val isAuthenticated = preferences.isAuthenticated.first()
                delay(2000)

                startDestination = when {
                    isAuthenticated -> Routes.Home
                    else -> Routes.Login
                }
            }

            Crossfade(
                targetState = startDestination,
                label = "splashToApp"
            ) { state ->
                when (state) {
                    null -> Splash()
                    else -> AppNavigation(startDestination = state)
                }
            }
        }
    }
}