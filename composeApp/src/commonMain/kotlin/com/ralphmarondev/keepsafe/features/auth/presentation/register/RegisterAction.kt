package com.ralphmarondev.keepsafe.features.auth.presentation.register

sealed interface RegisterAction {
    data class FamilyIdChange(val familyId: String) : RegisterAction
    data class FamilyNameChange(val familyName: String) : RegisterAction
    data class EmailChange(val email: String) : RegisterAction
    data class PasswordChange(val password: String) : RegisterAction
    data class ConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object Register : RegisterAction
    data object IncrementCurrentPage : RegisterAction
    data object DecrementCurrentPage : RegisterAction
    data object ShowNavigateBackDialog : RegisterAction
    data object CloseNavigateBackDialog : RegisterAction
    data object NavigateToLogin : RegisterAction
    data object ClearNavigation : RegisterAction
}