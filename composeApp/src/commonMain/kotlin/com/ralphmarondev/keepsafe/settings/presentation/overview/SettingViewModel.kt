package com.ralphmarondev.keepsafe.settings.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.settings.domain.usecase.SyncWithFirebaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preferences: AppPreferences,
    private val userDao: UserDao,
    private val syncWithFirebaseUseCase: SyncWithFirebaseUseCase
) : ViewModel() {

    private val _showNotification = MutableStateFlow(false)
    val showNotifications = _showNotification.asStateFlow()

    private val _showSyncWithFirebaseDialog = MutableStateFlow(false)
    val showSyncWithFirebaseDialog = _showSyncWithFirebaseDialog.asStateFlow()

    private val _showConfirmLogoutDialog = MutableStateFlow(false)
    val showConfirmLogoutDialog = _showConfirmLogoutDialog.asStateFlow()

    private val _syncResponse = MutableStateFlow<Result?>(null)
    val syncResponse = _syncResponse.asStateFlow()

    private val _logoutResponse = MutableStateFlow<Result?>(null)
    val logoutResponse = _logoutResponse.asStateFlow()


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

    fun setShowSyncWithFirebaseDialog(value: Boolean) {
        _showSyncWithFirebaseDialog.value = value
    }

    fun syncWithFirebase() {
        viewModelScope.launch {
            _syncResponse.value = syncWithFirebaseUseCase()
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
                _logoutResponse.value = Result(
                    success = true,
                    message = "Account removed successfully."
                )
            } catch (e: Exception) {
                _logoutResponse.value = Result(
                    success = false,
                    message = "Error: ${e.message}"
                )
            }
        }
    }
}