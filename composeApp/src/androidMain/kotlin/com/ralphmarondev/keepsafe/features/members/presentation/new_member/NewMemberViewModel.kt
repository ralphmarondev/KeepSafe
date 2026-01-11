package com.ralphmarondev.keepsafe.features.members.presentation.new_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewMemberViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewMemberState())
    val state = _state.asStateFlow()

    fun onAction(action: NewMemberAction) {
        when (action) {
            NewMemberAction.Save -> save()

            NewMemberAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            NewMemberAction.ClearNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }

            NewMemberAction.IncrementCurrentPage -> {
                _state.update { it.copy(currentPage = it.currentPage + 1) }
            }

            NewMemberAction.DecrementCurrentPage -> {
                _state.update { it.copy(currentPage = it.currentPage - 1) }
            }

            is NewMemberAction.AllergiesChange -> {
                _state.update { it.copy(allergies = action.allergies) }
            }

            is NewMemberAction.BirthdayChange -> {
                _state.update { it.copy(birthday = action.birthday) }
            }

            is NewMemberAction.BirthplaceChange -> {
                _state.update { it.copy(birthplace = action.birthplace) }
            }

            is NewMemberAction.BloodTypeChange -> {
                _state.update { it.copy(bloodType = action.bloodType) }
            }

            is NewMemberAction.CivilStatusChange -> {
                _state.update { it.copy(civilStatus = action.civilStatus) }
            }

            is NewMemberAction.CurrentAddressChange -> {
                _state.update { it.copy(currentAddress = action.currentAddress) }
            }

            is NewMemberAction.EmergencyContactChange -> {
                _state.update { it.copy(emergencyContact = action.emergencyContact) }
            }

            is NewMemberAction.FirstNameChange -> {
                _state.update { it.copy(firstName = action.firstName) }
            }

            is NewMemberAction.GenderChange -> {
                _state.update { it.copy(gender = action.gender) }
            }

            is NewMemberAction.LastNameChange -> {
                _state.update { it.copy(lastName = action.lastName) }
            }

            is NewMemberAction.MaidenNameChange -> {
                _state.update { it.copy(maidenName = action.maidenName) }
            }

            is NewMemberAction.MedicalConditionsChange -> {
                _state.update { it.copy(medicalConditions = action.medicalConditions) }
            }

            is NewMemberAction.MiddleNameChange -> {
                _state.update { it.copy(middleName = action.middleName) }
            }

            is NewMemberAction.NicknameChange -> {
                _state.update { it.copy(nickName = action.nickname) }
            }

            is NewMemberAction.PermanentAddressChange -> {
                _state.update { it.copy(permanentAddress = action.permanentAddress) }
            }

            is NewMemberAction.PhoneNumberChange -> {
                _state.update { it.copy(phoneNumber = action.phoneNumber) }
            }

            is NewMemberAction.PhotoUrlChange -> {
                _state.update { it.copy(photoUrl = action.photoUrl) }
            }

            is NewMemberAction.ReligionChange -> {
                _state.update { it.copy(religion = action.religion) }
            }

            is NewMemberAction.EmailChange -> {
                _state.update { current ->
                    val isValid = action.email.contains("@")
                    current.copy(
                        email = action.email,
                        isValidEmail = isValid,
                        emailSupportingText = if (!isValid && action.email.isNotEmpty()) {
                            "Please enter valid email address"
                        } else {
                            ""
                        }
                    )
                }
            }

            is NewMemberAction.PasswordChange -> {
                _state.update { current ->
                    val isValid = action.password.length >= 8
                    current.copy(
                        password = action.password,
                        isValidPassword = isValid,
                        passwordSupportingText = if (!isValid && action.password.isNotEmpty()) {
                            "Password must be at least 8 characters"
                        } else {
                            ""
                        }
                    )
                }
            }

            is NewMemberAction.ConfirmPasswordChange -> {
                _state.update { current ->
                    val isValid =
                        action.confirmPassword.length >= 8 && action.confirmPassword == _state.value.password
                    current.copy(
                        confirmPassword = action.confirmPassword,
                        isValidConfirmPassword = isValid,
                        confirmPasswordSupportingText = if (!isValid && action.confirmPassword.isNotEmpty()) {
                            "Password did not match"
                        } else {
                            ""
                        }
                    )
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }
            val user = User(
                familyId = _state.value.familyId,
                familyName = _state.value.familyName,
                email = _state.value.email,
                password = _state.value.password,
                role = Role.FAMILY_MEMBER.name,
                active = false,
                firstName = _state.value.firstName,
                middleName = _state.value.middleName,
                maidenName = _state.value.maidenName,
                lastName = _state.value.lastName,
                nickname = _state.value.nickName,
                civilStatus = _state.value.civilStatus,
                religion = _state.value.religion,
                gender = _state.value.gender,
                birthday = _state.value.birthday,
                birthplace = _state.value.birthplace,
                currentAddress = _state.value.currentAddress,
                permanentAddress = _state.value.permanentAddress,
                phoneNumber = _state.value.phoneNumber,
                photoUri = _state.value.photoUrl,
                bloodType = _state.value.bloodType,
                allergies = _state.value.allergies,
                emergencyContact = _state.value.emergencyContact,
                medicalConditions = _state.value.medicalConditions
            )

            val result = repository.saveNewMember(user)
            if (result.isSuccess) {
                _state.update { it.copy(isRegistered = true, isRegistering = false) }
                delay(3000)
                _state.update { it.copy(navigateBack = true) }
            } else {
                _state.update {
                    it.copy(
                        isError = true,
                        isRegistering = false,
                        errorMessage = "Failed registering family member."
                    )
                }
            }
        }
    }
}