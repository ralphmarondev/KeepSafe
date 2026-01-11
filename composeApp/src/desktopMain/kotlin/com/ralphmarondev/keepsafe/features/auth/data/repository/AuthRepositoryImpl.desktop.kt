package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

actual class AuthRepositoryImpl :
    AuthRepository {
    actual override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        TODO("Not yet implemented")
    }

    actual override suspend fun register(user: User): Result<User> {
        TODO("Not yet implemented")
    }

    actual override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        TODO("Not yet implemented")
    }
}