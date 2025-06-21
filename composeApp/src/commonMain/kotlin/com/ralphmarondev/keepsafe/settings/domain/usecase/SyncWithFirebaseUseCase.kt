package com.ralphmarondev.keepsafe.settings.domain.usecase

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.settings.domain.repository.SettingRepository

class SyncWithFirebaseUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): Result {
        return try {
            repository.syncWithFirebase()
            Result(
                success = true,
                message = "Synced successfully!"
            )
        } catch (e: Exception) {
            Result(
                success = false,
                message = "Error: ${e.message}"
            )
        }
    }
}