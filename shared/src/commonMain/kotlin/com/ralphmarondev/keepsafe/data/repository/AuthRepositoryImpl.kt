package com.ralphmarondev.keepsafe.data.repository

import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.data.network.firebase.FamilyService
import com.ralphmarondev.keepsafe.data.network.firebase.MemberService
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val familyService: FamilyService,
    private val memberService: MemberService
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
        val authResult = authService.register(account)
        if (authResult is Result.Error) {
            return Result.Error(authResult.message)
        }
        val createdAccount = (authResult as Result.Success).data

        val familyResult = familyService.createFamily(family)
        if (familyResult is Result.Error) {
            return Result.Error(familyResult.message)
        }
        val createdFamily = (familyResult as Result.Success).data
        val memberToCreate = member.copy(
            uid = createdAccount.uid,
            familyCode = createdFamily.code
        )
        val memberResult = memberService.createMember(memberToCreate)
        if (memberResult is Result.Error) {
            return Result.Error(memberResult.message)
        }
        return Result.Success(createdFamily)
    }
}