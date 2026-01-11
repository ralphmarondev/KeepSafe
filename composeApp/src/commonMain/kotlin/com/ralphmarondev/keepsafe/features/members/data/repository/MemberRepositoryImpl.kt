package com.ralphmarondev.keepsafe.features.members.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository

expect class MemberRepositoryImpl : MemberRepository {
    override suspend fun getMemberByEmail(email: String): User?
    override suspend fun getAllMembers(): List<User>
    override suspend fun saveNewMember(user: User): Result<User>
    override suspend fun isCurrentUserAdmin(): Boolean
}