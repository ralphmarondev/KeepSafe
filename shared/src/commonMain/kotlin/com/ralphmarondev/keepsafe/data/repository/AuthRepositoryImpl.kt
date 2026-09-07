package com.ralphmarondev.keepsafe.data.repository

import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val preferences: AppPreferences,
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
        familyCode: String
    ): Result<Member> {
        val result = authService.login(
            username = username,
            password = password,
            familyCode = familyCode
        )

        if (result.isSuccess) {
            val member = (result as Result.Success).data
            preferences.setUsername(username)
            preferences.setFamilyCode(familyCode)
            preferences.setFamilyName(member.lastName)
        }
        return result
    }

    override suspend fun logout(): Result<Unit> {
        val result = authService.logout()

        if (result.isSuccess) {
            preferences.logout()
        }
        return result
    }

    override suspend fun register(
        family: Family,
        member: Member,
        account: Account
    ): Result<Family> {
        val result = authService.register(
            family = family,
            member = member,
            account = account
        )

        if (result.isSuccess) {
            val familyResult = (result as Result.Success).data
            preferences.setUsername(account.username)
            preferences.setFamilyCode(familyResult.code)
            preferences.setFamilyName(familyResult.name)
        }
        return result
    }

    override suspend fun isUsernameTaken(username: String): Boolean {
        return authService.isUsernameTaken(username)
    }

    override suspend fun generateFamilyCode(): String {
        return authService.generateFamilyCode()
    }
}