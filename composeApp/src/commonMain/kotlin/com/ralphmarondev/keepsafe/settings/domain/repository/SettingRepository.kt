package com.ralphmarondev.keepsafe.settings.domain.repository

interface SettingRepository {

    suspend fun syncWithFirebase()

    suspend fun logout()
}