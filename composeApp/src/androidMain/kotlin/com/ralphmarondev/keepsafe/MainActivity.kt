package com.ralphmarondev.keepsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeProvider
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val preferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProvider(preferences = preferences) {
                val themeState = LocalThemeState.current

                KeepSafeTheme(
                    darkTheme = themeState.darkTheme.value
                ) {
                    AppNavigation()
                }
            }
        }
    }
}