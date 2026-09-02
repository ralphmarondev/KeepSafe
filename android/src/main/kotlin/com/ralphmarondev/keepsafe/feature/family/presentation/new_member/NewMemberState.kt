package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

import com.ralphmarondev.keepsafe.feature.family.domain.enums.MemberRegistrationPage

data class NewMemberState(
    val firstName: String = "",
    val firstNameError: Boolean = false,
    val firstNameErrorMessage: String? = null,

    val lastName: String = "",
    val lastNameError: Boolean = false,
    val lastNameErrorMessage: String? = null,

    val middleName: String = "",
    val middleNameError: Boolean = false,
    val middleNameErrorMessage: String? = null,

    val maidenName: String = "",
    val maidenNameError: Boolean = false,
    val maidenNameErrorMessage: String? = null,

    val birthday: String = "",
    val birthdayError: Boolean = false,
    val birthdayErrorMessage: String? = null,

    val contactNumber: String = "",
    val contactNumberError: Boolean = false,
    val contactNumberErrorMessage: String? = null,

    // account
    val username: String = "",
    val usernameError: Boolean = false,
    val usernameErrorMessage: String? = null,

    val password: String = "",
    val passwordError: Boolean = false,
    val passwordErrorMessage: String? = null,

    val confirmPassword: String = "",
    val confirmPasswordError: Boolean = false,
    val confirmPasswordErrorMessage: String? = null,

    val errorMessage: String? = null,
    val showErrorMessage: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val page: MemberRegistrationPage = MemberRegistrationPage.BasicInformation
)
