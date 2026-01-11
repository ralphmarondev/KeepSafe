package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

expect class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User>

    override suspend fun register(user: User): Result<User>
    override suspend fun isFamilyIdTaken(familyId: String): Boolean
}