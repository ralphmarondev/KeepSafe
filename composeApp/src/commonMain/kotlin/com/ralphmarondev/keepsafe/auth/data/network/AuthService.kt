package com.ralphmarondev.keepsafe.auth.data.network

import com.ralphmarondev.keepsafe.auth.data.model.SignInRequest
import com.ralphmarondev.keepsafe.auth.data.model.SignInResponse
import com.ralphmarondev.keepsafe.core.domain.model.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthService(
    private val client: HttpClient
) {
    private val apiKey = "secret_bleh_hehehehe"

    suspend fun signInWithEmailPassword(email: String, password: String): Result {
        return try {
            val response = client.post(
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
            ) {
                setBody(SignInRequest(email = email, password = password))
            }

            if (response.status.value == 200) {
                val body: SignInResponse = response.body()
                Result(
                    success = true,
                    message = "Welcome back, ${body.email}"
                )
            } else {
                Result(
                    success = false,
                    message = "Authentication failed: ${response.status.description}"
                )
            }
        } catch (e: Exception) {
            Result(
                success = false,
                message = "Error: ${e.message ?: "Unknown error"}"
            )
        }
    }
}