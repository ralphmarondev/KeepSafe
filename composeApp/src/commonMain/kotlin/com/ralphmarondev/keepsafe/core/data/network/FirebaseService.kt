package com.ralphmarondev.keepsafe.core.data.network

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

expect class FirebaseService {
    suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User>

    suspend fun register(user: User): Result<User>

    suspend fun isFamilyIdTaken(familyId: String): Boolean

    suspend fun syncAndSave()
}