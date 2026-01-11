package com.ralphmarondev.keepsafe.features.members.presentation.member_details

import com.ralphmarondev.keepsafe.core.domain.model.User

data class MemberDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val navigateBack: Boolean = false,
    val errorMessage: String? = null,
    val member: User = User(),
)