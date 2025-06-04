package com.ralphmarondev.keepsafe.family_list.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.DARK_THEME_KEY
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FamilyListViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val darkMode: StateFlow<Boolean> = dataStore.data
        .map { preferences -> preferences[DARK_THEME_KEY] ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    fun toggleDarkMode() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val current = preferences[DARK_THEME_KEY] ?: false
                preferences[DARK_THEME_KEY] = !current
            }
        }
    }
}