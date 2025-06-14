package com.ralphmarondev.keepsafe.auth.domain.usecase

import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result

class LoginUseCase(
    private val repository: AuthRepository,
    private val preferences: AppPreferences
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): Result {
        if (username.isBlank() && password.isBlank()) {
            return Result(
                success = false,
                message = "Username and password cannot be blank."
            )
        }

        if (username.isBlank()) {
            return Result(
                success = false,
                message = "Username cannot be empty."
            )
        }

        if (password.isBlank()) {
            return Result(
                success = false,
                message = "Password cannot be empty."
            )
        }

        try {
            val tokens = repository.login(username = username, password = password)
            return if (tokens != null) {
                preferences.saveAuthTokens(
                    idToken = tokens.idToken,
                    refreshToken = tokens.refreshToken,
                    localId = tokens.localId,
                    email = tokens.email,
                    familyId = tokens.familyId,
                    fullName = tokens.fullName,
                    role = tokens.role
                )
                Result(
                    success = true,
                    message = "Login successful."
                )
            } else {
                Result(
                    success = false,
                    message = "Login failed. Invalid credentials."
                )
            }
        } catch (e: Exception) {
            return Result(
                success = false,
                message = "Login failed. Error: ${e.message}"
            )
        }
    }
}