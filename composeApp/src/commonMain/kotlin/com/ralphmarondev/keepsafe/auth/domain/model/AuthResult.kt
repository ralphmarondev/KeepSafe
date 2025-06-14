package com.ralphmarondev.keepsafe.auth.domain.model

data class AuthTokens(
    val idToken: String,
    val refreshToken: String?,
    val localId: String,
    val email: String,
    val fullName: String,
    val familyId: String,
    val role: String
)
