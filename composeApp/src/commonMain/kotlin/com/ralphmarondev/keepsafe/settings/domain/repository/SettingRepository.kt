package com.ralphmarondev.keepsafe.settings.domain.repository

interface SettingRepository {

    suspend fun syncWithFirebase()

    suspend fun logout()

    suspend fun getNotificationState(): Boolean

    suspend fun setNotificationState(value: Boolean)
}