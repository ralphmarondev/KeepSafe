package com.ralphmarondev.keepsafe.core.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ralphmarondev.keepsafe.core.data.local.DARK_THEME_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ThemeStateImpl(
    private val dataStore: DataStore<Preferences>
) : ThemeState {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _darkMode = MutableStateFlow(false)
    override val darkMode: StateFlow<Boolean> = _darkMode

    init {
        scope.launch {
            dataStore.data.map { it[DARK_THEME_KEY] == true }
                .collect { _darkMode.value = it }
        }
    }

    override fun toggleTheme() {
        scope.launch {
            dataStore.edit {
                val current = it[DARK_THEME_KEY] == true
                it[DARK_THEME_KEY] = !current
            }
        }
    }
}