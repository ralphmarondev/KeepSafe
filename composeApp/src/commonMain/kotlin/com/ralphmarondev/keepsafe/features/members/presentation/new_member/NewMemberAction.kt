package com.ralphmarondev.keepsafe.features.members.presentation.new_member

sealed interface NewMemberAction {
    data object Save : NewMemberAction
    data object NavigateBack : NewMemberAction
    data object ClearNavigation : NewMemberAction
    data object IncrementCurrentPage : NewMemberAction
    data object DecrementCurrentPage : NewMemberAction

    data class FirstNameChange(val firstName: String) : NewMemberAction
    data class MiddleNameChange(val middleName: String) : NewMemberAction
    data class LastNameChange(val lastName: String) : NewMemberAction
    data class MaidenNameChange(val maidenName: String) : NewMemberAction
    data class NicknameChange(val nickname: String) : NewMemberAction
    data class CivilStatusChange(val civilStatus: String) : NewMemberAction
    data class ReligionChange(val religion: String) : NewMemberAction
    data class GenderChange(val gender: String) : NewMemberAction
    data class BirthdayChange(val birthday: String) : NewMemberAction
    data class BirthplaceChange(val birthplace: String) : NewMemberAction
    data class CurrentAddressChange(val currentAddress: String) : NewMemberAction
    data class PermanentAddressChange(val permanentAddress: String) : NewMemberAction
    data class PhoneNumberChange(val phoneNumber: String) : NewMemberAction
    data class PickPhoto(val imagePath: String) : NewMemberAction
    data class BloodTypeChange(val bloodType: String) : NewMemberAction
    data class AllergiesChange(val allergies: String) : NewMemberAction
    data class MedicalConditionsChange(val medicalConditions: String) : NewMemberAction
    data class EmergencyContactChange(val emergencyContact: String) : NewMemberAction
    data class EmailChange(val email: String) : NewMemberAction
    data class PasswordChange(val password: String) : NewMemberAction
    data class ConfirmPasswordChange(val confirmPassword: String) : NewMemberAction
}