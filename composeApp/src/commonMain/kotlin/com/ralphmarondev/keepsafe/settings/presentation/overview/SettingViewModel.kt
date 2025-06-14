package com.ralphmarondev.keepsafe.settings.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    init {
        viewModelScope.launch {
            _fullName.value = preferences.fullName().first() ?: "No Name"
        }
    }
}