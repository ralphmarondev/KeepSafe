package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.dependencies.DbClient

class AuthRepositoryImpl(
    private val dbClient: DbClient
) : AuthRepository {
    override suspend fun login(username: String, password: String) {
        if (username == "ralphmaron" && password == "iscute") {
            println("Login successful.")
        } else {
            println("Login failed.")
        }
    }
}