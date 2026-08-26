package com.ralphmarondev.keepsafe.domain.repository

import com.ralphmarondev.keepsafe.domain.model.Member

interface MemberRepository {
    suspend fun getMembers(): List<Member>
}