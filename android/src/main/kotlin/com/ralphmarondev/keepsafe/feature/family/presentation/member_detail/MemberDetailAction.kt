package com.ralphmarondev.keepsafe.feature.family.presentation.member_detail

sealed interface MemberDetailAction {
    data object Refresh : MemberDetailAction
    data object LoadInformation : MemberDetailAction
}