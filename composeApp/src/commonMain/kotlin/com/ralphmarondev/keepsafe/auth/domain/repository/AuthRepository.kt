package com.ralphmarondev.keepsafe.auth.domain.repository

interface AuthRepository {

    suspend fun login(username: String, password: String)
}