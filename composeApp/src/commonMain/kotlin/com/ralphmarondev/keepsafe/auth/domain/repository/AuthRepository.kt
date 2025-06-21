package com.ralphmarondev.keepsafe.auth.domain.repository

import com.ralphmarondev.keepsafe.auth.domain.model.LoginResult

interface AuthRepository {

    suspend fun login(username: String, password: String): LoginResult?

    suspend fun saveLoginResult(result: LoginResult)
}