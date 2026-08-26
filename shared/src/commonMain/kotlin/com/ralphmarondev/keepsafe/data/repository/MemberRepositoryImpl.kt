package com.ralphmarondev.keepsafe.data.repository

import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import com.ralphmarondev.keepsafe.data.network.firebase.MemberService
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
import kotlinx.coroutines.flow.first

class MemberRepositoryImpl(
    private val preferences: AppPreferences,
    private val memberService: MemberService
) : MemberRepository {
    override suspend fun getMembers(): List<Member> {
        val familyCode = preferences.familyCode.first()
            ?: return emptyList()

        return when (val result = memberService.getMembersByFamilyCode(familyCode)) {
            is Result.Success -> result.data
            is Result.Error -> emptyList()
        }
    }
}