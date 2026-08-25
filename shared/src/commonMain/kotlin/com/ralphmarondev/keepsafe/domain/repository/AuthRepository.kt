package com.ralphmarondev.keepsafe.domain.repository

import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result

interface AuthRepository {
    suspend fun login(username: String, password: String, familyCode: String): Result<Member>
    suspend fun register(family: Family, member: Member, account: Account): Result<Family>
    suspend fun isUsernameTaken(username: String): Boolean
}