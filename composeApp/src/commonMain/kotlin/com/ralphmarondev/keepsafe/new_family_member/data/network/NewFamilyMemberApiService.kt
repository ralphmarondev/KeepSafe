package com.ralphmarondev.keepsafe.new_family_member.data.network

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class NewFamilyMemberApiService(
    private val client: HttpClient
) {
    suspend fun registerNewFamilyMember(
        email: String,
        password: String,
        fullName: String,
        role: String,
        birthday: String,
        birthplace: String,
        familyId: String
    ): Boolean {
        try {
            // Step 1: Register with Firebase Auth
            val authResponse =
                client.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${Secrets.apiKey}") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        RegisterRequest(
                            email = email,
                            password = password,
                            returnSecureToken = true
                        )
                    )
                }

            if (!authResponse.status.value.toString().startsWith("2")) {
                println("Auth registration failed: ${authResponse.bodyAsText()}")
                return false
            }

            val registeredUser = authResponse.body<RegisterResponse>()

            // Step 2: Save user data to Firestore
            val firestoreUrl = "${Secrets.userDetailsUrl}/${registeredUser.localId}"

            val saveResponse = client.patch(firestoreUrl) {
                contentType(ContentType.Application.Json)
                headers.append(HttpHeaders.Authorization, "Bearer ${registeredUser.idToken}")
                setBody(
                    Json.encodeToString(
                        FirestoreUserDocument(
                            fields = FirestoreUserFields(
                                email = FirestoreStringValue(email),
                                fullName = FirestoreStringValue(fullName),
                                role = FirestoreStringValue(role),
                                birthday = FirestoreStringValue(birthday),
                                birthplace = FirestoreStringValue(birthplace),
                                familyId = FirestoreStringValue(familyId),
                                isDeleted = FirestoreBooleanValue(false)
                            )
                        )
                    )
                )
            }

            if (!saveResponse.status.value.toString().startsWith("2")) {
                println("Firestore save failed: ${saveResponse.bodyAsText()}")
                return false
            }

            return true
        } catch (e: Exception) {
            println("Exception during registration: ${e.message}")
            return false
        }
    }

    // Auth Registration Request & Response
    @Serializable
    private data class RegisterRequest(
        val email: String,
        val password: String,
        val returnSecureToken: Boolean
    )

    @Serializable
    private data class RegisterResponse(
        val idToken: String,
        val email: String,
        val refreshToken: String,
        val expiresIn: String,
        val localId: String
    )

    // Firestore User Document
    @Serializable
    private data class FirestoreUserDocument(
        val fields: FirestoreUserFields
    )

    @Serializable
    private data class FirestoreUserFields(
        val email: FirestoreStringValue,
        val fullName: FirestoreStringValue,
        val role: FirestoreStringValue,
        val birthday: FirestoreStringValue,
        val birthplace: FirestoreStringValue,
        val familyId: FirestoreStringValue,
        val isDeleted: FirestoreBooleanValue
    )

    @Serializable
    private data class FirestoreStringValue(val stringValue: String)

    @Serializable
    private data class FirestoreBooleanValue(val booleanValue: Boolean)
}