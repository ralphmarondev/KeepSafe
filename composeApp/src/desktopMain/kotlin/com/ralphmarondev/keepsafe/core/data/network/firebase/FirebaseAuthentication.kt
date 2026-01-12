package com.ralphmarondev.keepsafe.core.data.network.firebase

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

@Serializable
data class AuthResult(
    val localId: String,
    val email: String,
    val idToken: String,
    val displayName: String? = null
)

@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

class FirebaseAuthentication(
    private val client: HttpClient
) {
    private val apiKey = System.getProperty("FIREBASE_API_KEY") ?: error("MISSING_FIREBASE_API_KEY")

    suspend fun login(email: String, password: String): AuthResult {
        return client.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword") {
            url { parameters.append("key", apiKey) }
            setBody(AuthRequest(email = email, password = password))
        }.body<AuthResult>()
    }

    suspend fun register(email: String, password: String): AuthResult {
        return client.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp") {
            url { parameters.append("key", apiKey) }
            setBody(AuthRequest(email = email, password = password))
        }.body<AuthResult>()
    }
}