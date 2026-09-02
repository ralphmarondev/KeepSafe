package com.ralphmarondev.keepsafe.domain.repository

import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result

interface MemberRepository {
    suspend fun getMembers(): List<Member>
    suspend fun getFamilyInformation(): Result<Family>
    suspend fun create(
        member: Member,
        account: Account
    ): Result<Member>
}