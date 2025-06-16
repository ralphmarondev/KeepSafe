package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.model.LoginResult
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginRequest

class AuthRepositoryImpl(
    private val loginApiService: LoginApiService
) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResult? {
        val response = loginApiService.login(
            request = LoginRequest(
                email = username,
                password = password
            )
        )
        return LoginResult(
            email = response?.email ?: ""
        )
    }
}