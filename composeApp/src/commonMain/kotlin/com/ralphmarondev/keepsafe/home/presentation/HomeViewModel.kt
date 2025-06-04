package com.ralphmarondev.keepsafe.home.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _darkMode = MutableStateFlow(false)
    val darkMode = _darkMode.asStateFlow()

    fun toggleDarkMode() {
        _darkMode.value = !_darkMode.value
    }
}