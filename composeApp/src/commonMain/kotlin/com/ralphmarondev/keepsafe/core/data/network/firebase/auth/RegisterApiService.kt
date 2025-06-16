package com.ralphmarondev.keepsafe.core.data.network.firebase.auth

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

data class RegisterResponse(
    val kind: String,
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: String,
    val localId: String
)

class RegisterApiService(
    private val httpClient: HttpClient
) {
    suspend fun register(
        request: RegisterRequest
    ): RegisterResponse? {
        return try {
            val response =
                httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${Secrets.apiKey}") {
                    setBody(request)
                }

            if (!response.status.isSuccess()) {
                println("Registration failed: ${response.bodyAsText()}")
                return null
            }
            response.body()
        } catch (e: Exception) {
            println("Registration error: ${e.message}")
            null
        }
    }
}
