package com.ralphmarondev.keepsafe.features.members.presentation.member_list

sealed interface MemberListAction {
    data class NavigateToDetails(val email: String) : MemberListAction
    data object NavigateToAccount : MemberListAction
    data object NavigateToNotification : MemberListAction
    data object NavigateToNewFamilyMember : MemberListAction
    data object ClearNavigation : MemberListAction
    data object Refresh : MemberListAction
    data class Search(val query: String) : MemberListAction
}