package com.ralphmarondev.keepsafe.feature.auth.presentation.login

data class LoginState(
    val familyId: String = "",
    val email: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isError: Boolean = false,
    val showMessage: Boolean = false,
    val message: String? = null,
    val navigateToRegister: Boolean = false
)