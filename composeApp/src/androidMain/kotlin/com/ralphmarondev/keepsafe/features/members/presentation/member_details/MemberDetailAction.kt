package com.ralphmarondev.keepsafe.features.members.presentation.member_details

sealed interface MemberDetailAction {
    data object NavigateBack : MemberDetailAction
    data object ClearNavigation : MemberDetailAction
}