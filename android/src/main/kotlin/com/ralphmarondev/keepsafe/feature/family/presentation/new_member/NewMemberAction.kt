package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

import com.ralphmarondev.keepsafe.feature.family.domain.enums.MemberRegistrationPage

sealed interface NewMemberAction {
    data object Save : NewMemberAction
    data class ChangePage(val page: MemberRegistrationPage) : NewMemberAction
    data class FirstNameChange(val firstName: String) : NewMemberAction
    data class LastNameChange(val lastName: String) : NewMemberAction
    data class MiddleNameChange(val middleName: String) : NewMemberAction
    data class MaidenNameChange(val maidenName: String) : NewMemberAction
    data class BirthdayChange(val birthday: String) : NewMemberAction
    data class ContactNumberChange(val contactNumber: String) : NewMemberAction
    data class UsernameChange(val username: String) : NewMemberAction
    data class PasswordChange(val password: String) : NewMemberAction
    data class ConfirmPasswordChange(val confirmPassword: String) : NewMemberAction
}