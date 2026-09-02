package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

sealed interface NewMemberAction {
    data object Save : NewMemberAction
}