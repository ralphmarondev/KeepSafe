package com.ralphmarondev.keepsafe.auth.domain.usecase

import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.domain.model.Result

class LoginUseCase(
    private val repository: AuthRepository
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
            val result = repository.login(username = username, password = password)
            return if (
                !result?.email.isNullOrBlank() &&
                result.uid.isNotBlank() &&
                result.role.isNotBlank() &&
                result.familyId.isNotBlank() &&
                !result.isDeleted
            ) {
                repository.saveLoginResult(result = result)

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