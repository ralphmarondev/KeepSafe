package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.dependencies.DbClient

class AuthRepositoryImpl(
    private val dbClient: DbClient
) : AuthRepository {
    override suspend fun login(username: String, password: String): Boolean {
        return username == "ralphmaron" && password == "iscute"
    }
}