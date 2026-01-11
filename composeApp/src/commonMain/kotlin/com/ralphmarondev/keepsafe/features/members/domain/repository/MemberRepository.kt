package com.ralphmarondev.keepsafe.features.members.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

interface MemberRepository {
    suspend fun getMemberByEmail(email: String): User?

    suspend fun getAllMembers(): List<User>
    suspend fun saveNewMember(user: User): Result<User>

    suspend fun isCurrentUserAdmin(): Boolean
}