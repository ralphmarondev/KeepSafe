package com.ralphmarondev.keepsafe.settings.domain.usecase

import com.ralphmarondev.keepsafe.settings.domain.repository.SettingRepository

class NotificationUseCase(
    private val repository: SettingRepository
) {

    suspend fun set(value: Boolean) {
        repository.setNotificationState(value)
    }

    suspend fun get(): Boolean {
        return repository.getNotificationState() == true
    }
}