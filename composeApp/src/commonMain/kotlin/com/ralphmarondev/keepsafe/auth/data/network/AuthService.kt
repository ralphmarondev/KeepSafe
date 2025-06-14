package com.ralphmarondev.keepsafe.auth.data.network

import com.ralphmarondev.keepsafe.auth.data.model.SignInRequest
import com.ralphmarondev.keepsafe.auth.data.model.SignInResponse
import com.ralphmarondev.keepsafe.auth.domain.model.AuthTokens
import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

class AuthService(
    private val client: HttpClient
) {
    @Serializable
    data class FirestoreUserDocument(
        val fields: FirestoreUserFields
    )

    @Serializable
    data class FirestoreUserFields(
        val familyId: FirestoreStringValue,
        val role: FirestoreStringValue,
        val fullName: FirestoreStringValue
    )

    @Serializable
    data class FirestoreStringValue(
        val stringValue: String
    )

    suspend fun signInWithEmailPassword(email: String, password: String): AuthTokens? {
        return try {
            val response = client.post(Secrets.authUrl) {
                setBody(SignInRequest(email = email, password = password))
            }

            if (response.status.value == 200) {
                val signInBody: SignInResponse = response.body()
                val idToken = signInBody.idToken
                val userId = signInBody.localId

                val userDoc = client.get("${Secrets.userDetailsUrl}/$userId") {
                    headers {
                        append("Authorization", "Bearer $idToken")
                    }
                }.body<FirestoreUserDocument>()
                val fields = userDoc.fields

                AuthTokens(
                    idToken = signInBody.idToken,
                    refreshToken = signInBody.refreshToken,
                    localId = signInBody.localId,
                    email = signInBody.email,
                    familyId = fields.familyId.stringValue,
                    fullName = fields.fullName.stringValue
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}