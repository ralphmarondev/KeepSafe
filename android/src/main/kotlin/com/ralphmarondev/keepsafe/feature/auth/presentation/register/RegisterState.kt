package com.ralphmarondev.keepsafe.feature.auth.presentation.register

data class RegisterState(
    // family
    val familyCode: String = "",
    val familyName: String = "",
    // member info
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String? = null,
    val maidenName: String? = null,
    // account
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
