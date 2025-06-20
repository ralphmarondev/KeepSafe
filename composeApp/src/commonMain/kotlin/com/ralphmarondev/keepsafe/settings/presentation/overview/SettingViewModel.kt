package com.ralphmarondev.keepsafe.settings.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preferences: AppPreferences,
    private val userDao: UserDao
) : ViewModel() {

    private val _showNotification = MutableStateFlow(false)
    val showNotifications = _showNotification.asStateFlow()

    private val _showConfirmLogoutDialog = MutableStateFlow(false)
    val showConfirmLogoutDialog = _showConfirmLogoutDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    init {
        viewModelScope.launch {
            _showNotification.value = preferences.notification().first() == true
        }
    }

    fun toggleShowNotification() {
        viewModelScope.launch {
            _showNotification.value = !_showNotification.value
            preferences.setNotification(_showNotification.value)
        }
    }

    fun setShowConfirmLogoutDialog(value: Boolean) {
        _showConfirmLogoutDialog.value = value
    }

    fun logout() {
        viewModelScope.launch {
            try {
                preferences.clearAccountInfo()
                preferences.setFirstTimeReadingFamilyList(true)
                userDao.deleteAllUsers()
                _response.value = Result(
                    success = true,
                    message = "Account removed successfully."
                )
            } catch (e: Exception) {
                _response.value = Result(
                    success = false,
                    message = "Error: ${e.message}"
                )
            }
        }
    }
}