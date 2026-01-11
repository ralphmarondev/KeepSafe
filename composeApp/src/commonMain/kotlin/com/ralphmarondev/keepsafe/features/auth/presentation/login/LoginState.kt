package com.ralphmarondev.keepsafe.features.auth.presentation.login

data class LoginState(
    val familyId: String = "",
    val email: String = "",
    val password: String = "",
    val familyIdSupportingText: String = "",
    val emailSupportingText: String = "",
    val passwordSupportingText: String = "",
    val isValidFamilyId: Boolean = true,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val register: Boolean = false
)