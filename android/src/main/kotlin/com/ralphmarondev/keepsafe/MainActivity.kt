package com.ralphmarondev.keepsafe

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.navigation.Route
import com.ralphmarondev.keepsafe.presentation.theme.KeepsafeTheme
import com.ralphmarondev.keepsafe.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.presentation.theme.ThemeProvider
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProvider(preferences = preferences) {
                val themeState = LocalThemeState.current
                var startDestination by remember { mutableStateOf<Route?>(null) }

                LaunchedEffect(Unit) {
                    val isAuthenticated = preferences.familyCode.first().isNullOrBlank()
                    startDestination = if (isAuthenticated) {
                        Route.Auth
                    } else {
                        Route.Main
                    }
                }

                val view = LocalView.current
                if (!view.isInEditMode) {
                    SideEffect {
                        val window = (view.context as Activity).window
                        val insetsController = window?.let {
                            WindowCompat.getInsetsController(it, view)
                        }
                        insetsController?.isAppearanceLightStatusBars = themeState.darkMode.value
                    }
                }

                KeepsafeTheme(darkTheme = themeState.darkMode.value) {
                    startDestination?.let { destination ->
                        AppNavigation(startDestination = destination)
                    }
                }
            }
        }
    }
}