package com.ralphmarondev.keepsafe.features.auth.presentation.register

data class RegisterState(
    val familyId: String = "",
    val familyName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val familyIdSupportingText: String = "",
    val familyNameSupportingText: String = "",
    val emailSupportingText: String = "",
    val passwordSupportingText: String = "",
    val confirmPasswordSupportingText: String = "",

    val isValidFamilyId: Boolean = true,
    val isValidFamilyName: Boolean = true,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val isValidConfirmPassword: Boolean = true,

    val isFamilyIdChangedManually: Boolean = false,
    val isRegistering: Boolean = false,
    val isRegistered: Boolean = false,
    val errorMessage: String? = null,
    val currentPage: Int = 0,
    val navigateToLogin: Boolean = false,
    val showNavigateBackDialog: Boolean = false
)