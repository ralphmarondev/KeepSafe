package com.ralphmarondev.keepsafe.features.auth.presentation.login

data class LoginState(
    val familyId: String = "",
    val email: String = "",
    val password: String = "",
    val familyIdSupportingText: String = "",
    val emailSupportingText: String = "",
    val passwordSupportingText: String = "",
    val isValidFamilyId: Boolean = false,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val register: Boolean = false
)
