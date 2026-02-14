package com.ralphmarondev.keepsafe.features.auth.presentation.login

sealed interface LoginAction {
    data class FamilyIdChange(val familyId: String) : LoginAction
    data class EmailChange(val email: String) : LoginAction
    data class PasswordChange(val password: String) : LoginAction
    data object Login : LoginAction
    data object Register : LoginAction
}