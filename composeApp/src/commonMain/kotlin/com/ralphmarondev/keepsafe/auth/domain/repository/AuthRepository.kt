package com.ralphmarondev.keepsafe.auth.domain.repository

import com.ralphmarondev.keepsafe.auth.domain.model.AuthTokens

interface AuthRepository {

    suspend fun login(username: String, password: String): AuthTokens?
}