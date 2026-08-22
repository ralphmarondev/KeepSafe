package com.ralphmarondev.keepsafe.data.repository

import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
        familyCode: String
    ): Result<Member> {
        return Result.Success(Member(familyCode = familyCode))
    }

    override suspend fun register(
        family: Family,
        member: Member
    ): Result<Family> {
        return Result.Success(family)
    }
}