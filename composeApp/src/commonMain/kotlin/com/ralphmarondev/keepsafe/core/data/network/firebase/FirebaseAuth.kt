package com.ralphmarondev.keepsafe.core.data.network.firebase

data class AuthResult(
    val uid: String = "",
    val email: String = "",
    val displayName: String? = null
)

class FirebaseAuth {
    suspend fun login(email: String, password: String): AuthResult {
        return AuthResult(uid = "1234", email = email)
    }

    suspend fun register(email: String, password: String): AuthResult {
        return AuthResult(uid = "1234", email = email)
    }
}

