package com.ralphmarondev.keepsafe.feature.auth.presentation.register

enum class RegistrationPage {
    FamilyInformation,
    MemberInformation,
    AccountInformation
}

data class RegisterState(
    // family
    val familyCode: String = "",
    val familyName: String = "",
    // member info
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    // account
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val login: Boolean = false,
    val message: String? = null,
    val showMessage: Boolean = false,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val page: RegistrationPage = RegistrationPage.FamilyInformation
)
