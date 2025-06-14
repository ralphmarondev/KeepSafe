package com.ralphmarondev.keepsafe.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex = _selectedIndex.asStateFlow()

    private val _role = MutableStateFlow("")
    val role = _role.asStateFlow()

    init {
        viewModelScope.launch {
            _role.value = preferences.role().first() ?: "No role provided"
        }
    }

    fun setSelectedIndex(value: Int) {
        _selectedIndex.value = value
    }
}