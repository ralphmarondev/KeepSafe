package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

sealed interface MemberListAction {
    data object LoadMembers : MemberListAction
}