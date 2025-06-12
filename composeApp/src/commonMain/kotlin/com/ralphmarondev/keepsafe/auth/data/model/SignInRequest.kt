package com.ralphmarondev.keepsafe.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest (
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)