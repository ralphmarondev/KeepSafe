package com.ralphmarondev.keepsafe.family_list.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.DARK_THEME_KEY
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FamilyListViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val darkMode: StateFlow<Boolean> = dataStore.data
        .map { preferences -> preferences[DARK_THEME_KEY] ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()


    init {
        _familyMembers.value += FamilyMember(
            id = 0,
            firstName = "First Name 1",
            lastName = "LN 1",
            middleName = "MN 1",
            role = "Father",
            birthDay = "June 5, 2025",
            birthPlace = "Earth",
            contactNumber = "09123456789",
            email = "firstname1@gmail.com"
        )
        _familyMembers.value += FamilyMember(
            id = 1,
            firstName = "First Name 2",
            lastName = "LN 2",
            middleName = "MN 2",
            role = "Wife",
            birthDay = "June 4, 2025",
            birthPlace = "Mars",
            contactNumber = "09612345678",
            email = "firstname2@gmail.com"
        )
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val current = preferences[DARK_THEME_KEY] ?: false
                preferences[DARK_THEME_KEY] = !current
            }
        }
    }
}