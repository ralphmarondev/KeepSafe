package com.ralphmarondev.keepsafe.auth.domain.model

data class LoginResult(
    val email: String,
    val uid: String,
    val role: String,
    val familyId: String,
    val isDeleted: Boolean
)