package com.ralphmarondev.keepsafe.auth.data.network

import com.ralphmarondev.keepsafe.auth.data.model.SignInRequest
import com.ralphmarondev.keepsafe.auth.data.model.SignInResponse
import com.ralphmarondev.keepsafe.auth.domain.model.AuthTokens
import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class AuthService(
    private val client: HttpClient
) {
    @Serializable
    private data class FirestoreUserDocument(
        val fields: FirestoreUserFields
    )

    @Serializable
    private data class FirestoreUserFields(
        val familyId: FirestoreStringValue,
        val role: FirestoreStringValue,
        val fullName: FirestoreStringValue,
        val email: FirestoreStringValue
    )

    @Serializable
    private data class FirestoreStringValue(
        val stringValue: String
    )

    @Serializable
    private data class FirestoreUsersResponse(
        val documents: List<FirestoreUserDocument>
    )

    @Serializable
    private data class FirestoreErrorResponse(
        val error: FirestoreError
    )

    @Serializable
    private data class FirestoreError(
        val code: Int,
        val message: String,
        val status: String
    )

    suspend fun signInWithEmailPassword(email: String, password: String): AuthTokens? {
        return try {
            val response = client.post(Secrets.authUrl) {
                setBody(SignInRequest(email = email, password = password))
            }

            if (!response.status.isSuccess()) {
                println("Auth failed: ${response.bodyAsText()}")
                return null
            }

            val signInBody: SignInResponse = response.body()

            val userResponse: HttpResponse = client.get(Secrets.userDetailsUrl)

            if (!userResponse.status.isSuccess()) {
                val errorText = userResponse.bodyAsText()
                println("\nFirestore access failed: \n$errorText")
                try {
                    val error = Json.decodeFromString<FirestoreErrorResponse>(errorText)
                    println("${error.error.status} (${error.error.code}): ${error.error.message}")
                } catch (e: Exception) {
                    println("Could not parse firestore error")
                }
                return null
            }

            val userList = userResponse.body<FirestoreUsersResponse>()

            val matchedUser = userList.documents.firstOrNull {
                it.fields.email.stringValue == email
            } ?: run {
                println("No matching user found in Firestore.")
                return null
            }

            val fields = matchedUser.fields

            AuthTokens(
                idToken = signInBody.idToken,
                refreshToken = signInBody.refreshToken,
                localId = signInBody.localId,
                email = signInBody.email,
                familyId = fields.familyId.stringValue,
                fullName = fields.fullName.stringValue
            )
        } catch (e: Exception) {
            println("Exception during sign-in: ${e.message}")
            null
        }
    }
}