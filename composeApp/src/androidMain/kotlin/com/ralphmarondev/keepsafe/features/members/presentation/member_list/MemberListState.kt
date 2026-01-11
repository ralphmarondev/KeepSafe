package com.ralphmarondev.keepsafe.features.members.presentation.member_list

import com.ralphmarondev.keepsafe.core.domain.model.User

data class MemberListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val members: List<User> = emptyList(),
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val selectedMemberEmail: String = "",
    val navigateToDetails: Boolean = false,
    val navigateToAccount: Boolean = false,
    val navigateToNotification: Boolean = false,
    val navigateToNewFamilyMember: Boolean = false,
    val searchQuery: String = "",
    val isCurrentUserAdmin: Boolean = false
)
