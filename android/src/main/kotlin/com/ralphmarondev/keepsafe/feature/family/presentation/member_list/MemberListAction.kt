package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

import com.ralphmarondev.keepsafe.domain.model.Member

sealed interface MemberListAction {
    data object LoadMembers : MemberListAction
    data object Refresh : MemberListAction
    data object ClearNavigation : MemberListAction
    data object NavigateToProfile : MemberListAction
    data object NewMember : MemberListAction
    data class MemberSelected(val member: Member) : MemberListAction
}