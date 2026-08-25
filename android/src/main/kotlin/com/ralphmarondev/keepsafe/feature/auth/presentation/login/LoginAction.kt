package com.ralphmarondev.keepsafe.feature.auth.presentation.login

sealed interface LoginAction {
    data object Login : LoginAction
    data object Register : LoginAction
    data class FamilyCodeChange(val code: String) : LoginAction
    data class UsernameChange(val username: String) : LoginAction
    data class PasswordChange(val password: String) : LoginAction
}