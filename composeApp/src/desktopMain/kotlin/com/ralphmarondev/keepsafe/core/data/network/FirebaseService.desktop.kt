package com.ralphmarondev.keepsafe.core.data.network

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

actual class FirebaseService {
    actual suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        TODO("Not yet implemented")
    }

    actual suspend fun register(user: User): Result<User> {
        TODO("Not yet implemented")
    }

    actual suspend fun isFamilyIdTaken(familyId: String): Boolean {
        TODO("Not yet implemented")
    }
}