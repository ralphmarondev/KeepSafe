package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.domain.model.User

class FirebaseService {
    suspend fun getDetailsByEmail(email: String, familyId: String): UserEntity? {
        return UserEntity()
    }

    suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return false
    }

    suspend fun createFamily(
        familyId: String,
        familyName: String,
        createdBy: String
    ): Boolean {
        return true
    }

    suspend fun registerMemberToFamily(
        user: User,
        uid: String
    ): Boolean {
        return true
    }
}