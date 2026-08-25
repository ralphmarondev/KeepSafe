package com.ralphmarondev.keepsafe.feature.auth.presentation.login

data class LoginState(
    val familyCode: String = "",
    val familyCodeError: Boolean = false,
    val familyCodeErrorMessage: String? = null,

    val username: String = "",
    val usernameError: Boolean = false,
    val usernameErrorMessage: String? = null,

    val password: String = "",
    val passwordError: Boolean = false,
    val passwordErrorMessage: String? = null,

    val isLoggedIn: Boolean = false,
    val isLoggingIn: Boolean = false,
    val navigateToRegister: Boolean = false
)