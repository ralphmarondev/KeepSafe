package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import com.ralphmarondev.keepsafe.domain.enums.RelationshipToHead

enum class RegistrationPage {
    FamilyInformation,
    MemberInformation,
    AccountInformation
}

data class RegisterState(
    // family
    val familyCode: String = "",
    val familyCodeError: Boolean = false,
    val familyCodeErrorMessage: String? = null,

    val familyName: String = "",
    val familyNameError: Boolean = false,
    val familyNameErrorMessage: String? = null,
    // member info
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

    val relationToHead: RelationshipToHead = RelationshipToHead.SELF,
    val relationToHeadError: Boolean = false,
    val relationToHeadErrorMessage: String? = null,
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

    val login: Boolean = false,
    val message: String? = null,
    val showMessage: Boolean = false,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val page: RegistrationPage = RegistrationPage.FamilyInformation,

    val isFinished: Boolean = false,
    val showDialog: Boolean = false
)
