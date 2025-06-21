package com.ralphmarondev.keepsafe.settings.domain.usecase

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.settings.domain.repository.SettingRepository

class LogoutUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): Result {
        return try {
            repository.logout()
            Result(
                success = true,
                message = "Logout successful."
            )
        } catch (e: Exception) {
            Result(
                success = false,
                message = "Logout failed. Error: ${e.message}"
            )
        }
    }
}