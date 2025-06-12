package com.ralphmarondev.keepsafe.auth.data.network

import com.ralphmarondev.keepsafe.API_KEY
import com.ralphmarondev.keepsafe.auth.data.model.SignInRequest
import com.ralphmarondev.keepsafe.auth.data.model.SignInResponse
import com.ralphmarondev.keepsafe.auth.domain.model.AuthTokens
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

class AuthService(
    private val client: HttpClient
) {
    suspend fun signInWithEmailPassword(email: String, password: String): AuthTokens? {
        return try {
            val response = client.post(
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$API_KEY"
            ) {
                setBody(SignInRequest(email = email, password = password))
            }

            if (response.status.value == 200) {
                val body: SignInResponse = response.body()
                val responseText = response.bodyAsText()
                println("Raw firebase response: $responseText")
                AuthTokens(
                    idToken = body.idToken,
                    refreshToken = body.refreshToken,
                    localId = body.localId,
                    email = body.email
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}