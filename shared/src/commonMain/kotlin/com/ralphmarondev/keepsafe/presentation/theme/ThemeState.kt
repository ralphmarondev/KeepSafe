package com.ralphmarondev.keepsafe.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeState internal constructor(
    val darkMode: State<Boolean>,
    private val preferences: AppPreferences,
    private val scope: CoroutineScope
) {
    fun toggleTheme() {
        scope.launch {
            preferences.setDarkMode(!darkMode.value)
        }
    }
}

@Composable
fun rememberThemeState(preferences: AppPreferences): ThemeState {
    val darkModeFlow = preferences.isDarkMode
    val darkModeState = darkModeFlow.collectAsState(false)
    val scope = rememberCoroutineScope()

    return remember(key1 = preferences, key2 = darkModeState.value) {
        ThemeState(
            darkMode = darkModeState,
            preferences = preferences,
            scope = scope
        )
    }
}