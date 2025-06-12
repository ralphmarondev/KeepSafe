package com.ralphmarondev.keepsafe.core.theme

import com.ralphmarondev.keepsafe.core.data.local.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThemeStateImpl(
    private val preferences: AppPreferences
) : ThemeState {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _darkMode = MutableStateFlow(false)
    override val darkMode: StateFlow<Boolean> = _darkMode

    init {
        scope.launch {
            preferences.isDarkTheme().collectLatest { isDark ->
                _darkMode.value = isDark
            }
        }
    }

    override fun toggleTheme() {
        scope.launch {
            preferences.setDarkTheme(!_darkMode.value)
        }
    }
}