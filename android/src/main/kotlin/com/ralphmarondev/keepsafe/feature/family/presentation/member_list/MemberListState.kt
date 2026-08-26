package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member

data class MemberListState(
    val family: Family = Family(),
    val members: List<Member> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val navigateToProfile: Boolean = false
)