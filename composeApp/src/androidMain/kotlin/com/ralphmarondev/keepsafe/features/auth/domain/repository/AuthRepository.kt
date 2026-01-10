package com.ralphmarondev.keepsafe.features.auth.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

interface AuthRepository {
    suspend fun login(familyId: String, email: String, password: String): Result<User>
    suspend fun register(user: User): Result<User>
    suspend fun isFamilyIdTaken(familyId: String): Boolean
}