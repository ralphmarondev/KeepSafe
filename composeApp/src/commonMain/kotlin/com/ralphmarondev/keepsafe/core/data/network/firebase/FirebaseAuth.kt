package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.data.network.firebase.dto.FirebaseAuthRequest
import com.ralphmarondev.keepsafe.core.data.network.firebase.dto.FirebaseAuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class FirebaseAuth(
    private val client: HttpClient
) {
    suspend fun login(email: String, password: String): FirebaseAuthResponse {
        return client.post(
            "${FirebaseConfig.AUTH_BASE_URL}/accounts:signInWithPassword"
        ) {
            url {
                parameters.append("key", FirebaseConfig.API_KEY)
            }
            setBody(FirebaseAuthRequest(email, password))
        }.body<FirebaseAuthResponse>()
    }

    suspend fun register(email: String, password: String): FirebaseAuthResponse {
        return client.post(
            "${FirebaseConfig.AUTH_BASE_URL}/accounts:signUp"
        ) {
            url {
                parameters.append("key", FirebaseConfig.API_KEY)
            }
            setBody(FirebaseAuthRequest(email, password))
        }.body<FirebaseAuthResponse>()
    }
}