package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.network.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebase: FirebaseService
) : AuthRepository {
    override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        return firebase.login(familyId, email, password)
    }

    override suspend fun register(user: User): Result<User> {
        return firebase.register(user)
    }

    override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return firebase.isFamilyIdTaken(familyId)
    }
}