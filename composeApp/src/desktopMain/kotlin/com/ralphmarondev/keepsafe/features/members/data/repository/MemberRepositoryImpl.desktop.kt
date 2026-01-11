package com.ralphmarondev.keepsafe.features.members.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository

actual class MemberRepositoryImpl :
    MemberRepository {
    actual override suspend fun getMemberByEmail(email: String): User? {
        return User()
    }

    actual override suspend fun getAllMembers(): List<User> {
        return emptyList()
    }

    actual override suspend fun saveNewMember(user: User): Result<User> {
        return Result.Success(user)
    }

    actual override suspend fun isCurrentUserAdmin(): Boolean {
        return true
    }
}