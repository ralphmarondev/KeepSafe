package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.data.network.AuthService
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.domain.model.Result

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(username: String, password: String): Boolean {
        val result: Result = authService.signInWithEmailPassword(username, password)
        return result.success
    }
}