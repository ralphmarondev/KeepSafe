package com.ralphmarondev.keepsafe.features.members.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository

actual class MemberRepositoryImpl :
    MemberRepository {
    actual override suspend fun getMemberByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    actual override suspend fun getAllMembers(): List<User> {
        TODO("Not yet implemented")
    }

    actual override suspend fun saveNewMember(user: User): Result<User> {
        TODO("Not yet implemented")
    }

    actual override suspend fun isCurrentUserAdmin(): Boolean {
        TODO("Not yet implemented")
    }
}