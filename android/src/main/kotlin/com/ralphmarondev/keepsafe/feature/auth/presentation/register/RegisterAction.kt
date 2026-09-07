package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import com.ralphmarondev.keepsafe.domain.enums.RelationshipToHead

sealed interface RegisterAction {
    data object Register : RegisterAction
    data object Login : RegisterAction
    data class ChangePage(val page: RegistrationPage) : RegisterAction
    data class FamilyNameChange(val name: String) : RegisterAction
    data class FirstNameChange(val firstName: String) : RegisterAction
    data class LastNameChange(val lastName: String) : RegisterAction
    data class MiddleNameChange(val middleName: String) : RegisterAction
    data class MaidenNameChange(val maidenName: String) : RegisterAction
    data class BirthdayChange(val birthday: String) : RegisterAction
    data class ContactNumberChange(val contactNumber: String) : RegisterAction
    data class RelationToHeadChange(val relationToHead: RelationshipToHead) : RegisterAction
    data class UsernameChange(val username: String) : RegisterAction
    data class PasswordChange(val password: String) : RegisterAction
    data class ConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object DismissDialog : RegisterAction
}