package com.ralphmarondev.keepsafe.feature.family.presentation.member_detail

import com.ralphmarondev.keepsafe.domain.model.Member

data class MemberDetailState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val showErrorMessage: Boolean = false,
    val errorMessage: String? = null,
    val member: Member = Member()
)