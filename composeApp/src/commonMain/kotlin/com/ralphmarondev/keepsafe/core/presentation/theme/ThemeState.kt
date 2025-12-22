package com.ralphmarondev.keepsafe.core.presentation.theme

import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface ThemeState {
    val darkTheme: StateFlow<Boolean>
    fun toggleTheme()
}

class ThemeStateImpl(
    private val preferences: AppPreferences
) : ThemeState {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _darkTheme = MutableStateFlow(false)
    override val darkTheme: StateFlow<Boolean> = _darkTheme

    init {
        scope.launch {
            preferences.isDarkTheme().collectLatest { isDark ->
                _darkTheme.value = isDark
            }
        }
    }

    override fun toggleTheme() {
        scope.launch {
            preferences.setDarkTheme(!_darkTheme.value)
        }
    }
}