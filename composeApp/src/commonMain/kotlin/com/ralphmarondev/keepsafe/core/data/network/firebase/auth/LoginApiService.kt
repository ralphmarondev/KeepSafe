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
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val kind: String,
    val localId: String,
    val email: String,
    val displayName: String? = null,
    val idToken: String,
    val registered: Boolean
)

class LoginApiService(
    private val httpClient: HttpClient
) {
    suspend fun login(
        request: LoginRequest
    ): LoginResponse? {
        return try {
            val response = httpClient.post(Secrets.LOGIN_URL) {
                setBody(request)
            }

            if (!response.status.isSuccess()) {
                println("Login failed: ${response.bodyAsText()}")
                return null
            }
            response.body()
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            null
        }
    }
}