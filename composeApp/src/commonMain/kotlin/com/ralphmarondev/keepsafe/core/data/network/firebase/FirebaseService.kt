package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity

class FirebaseService {
    suspend fun getDetailsByEmail(email: String, familyId: String): UserEntity? {
        return UserEntity()
    }
}