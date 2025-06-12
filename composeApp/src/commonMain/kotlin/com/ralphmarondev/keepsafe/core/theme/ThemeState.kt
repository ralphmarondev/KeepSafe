package com.ralphmarondev.keepsafe.core.theme

import kotlinx.coroutines.flow.StateFlow

interface ThemeState {
    val darkMode: StateFlow<Boolean>
    fun toggleTheme()
}
