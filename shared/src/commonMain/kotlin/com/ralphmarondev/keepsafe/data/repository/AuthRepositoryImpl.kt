package com.ralphmarondev.keepsafe.data.repository

import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
        familyCode: String
    ): Result<Member> {
        return Result.Success(Member(familyCode = familyCode))
    }

    override suspend fun register(
        family: Family,
        member: Member,
        account: Account
    ): Result<Family> {
        return authService.register(
            family = family,
            member = member,
            account = account
        )
    }

    override suspend fun isUsernameTaken(username: String): Boolean {
        return authService.isUsernameTaken(username)
    }

    override suspend fun generateFamilyCode(): String {
        return authService.generateFamilyCode()
    }
}