package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.data.network.AuthService
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.auth.domain.model.AuthTokens

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(username: String, password: String): AuthTokens? {
        return authService.signInWithEmailPassword(username, password)
    }
}