package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(username: String, password: String): Boolean {
        return username == "ralphmaron" && password == "iscute"
    }
}