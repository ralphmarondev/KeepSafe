package com.ralphmarondev.keepsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.presentation.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.presentation.theme.ThemeProvider
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.navigation.Routes
import kotlinx.coroutines.delay
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

                KeepSafeTheme(
                    darkTheme = themeState.darkTheme.value
                ) {
                    var startDestination by remember { mutableStateOf<Routes?>(null) }

                    LaunchedEffect(Unit) {
                        delay(2000)
                        val authenticated = preferences.isAuthenticated.first()

                        startDestination = when {
                            authenticated -> Routes.MemberList
                            else -> Routes.Login
                        }
                    }
                    startDestination?.let {
                        AppNavigation(startDestination = it)
                    }
                }
            }
        }
    }
}