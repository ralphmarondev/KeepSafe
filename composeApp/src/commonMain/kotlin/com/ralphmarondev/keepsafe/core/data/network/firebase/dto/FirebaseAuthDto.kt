package com.ralphmarondev.keepsafe.core.data.network.firebase.dto

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseAuthRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

@Serializable
data class FirebaseAuthResponse(
    val localId: String,
    val email: String,
    val displayName: String? = null,
    val idToken: String
)