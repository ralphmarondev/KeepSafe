package com.ralphmarondev.keepsafe.family.data.network

import com.ralphmarondev.keepsafe.core.util.Secrets
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class FamilyApiService(
    private val client: HttpClient
) {

    suspend fun getFamilyMembers(idToken: String, familyId: String): List<FamilyMember> {
        val url = Secrets.userDetailsUrl
        val response = client.get(url) {
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            throw Exception("Firestore access denied: $errorBody")
        }
        val body: FirestoreResponse = response.body()

        return body.documents
            .filter { doc ->
                val fields = doc.fields
                fields != null &&
                        fields.familyId.stringValue == familyId &&
                        fields.isDeleted?.booleanValue != true
            }
            .mapNotNull {
                it.toDomain()
            }
    }

    suspend fun getDetails(uid: String): FamilyMember? {
        return try {
            val userResponse: HttpResponse = client.get("${Secrets.userDetailsUrl}/$uid")
            val fields = userResponse.body<FirestoreUserFields>()

            FamilyMember(
                uid = uid,
                fullName = fields.fullName.stringValue,
                role = fields.role.stringValue,
                contactNumber = fields.contactNumber?.stringValue ?: "",
                birthplace = fields.birthplace?.stringValue ?: "",
                birthday = fields.birthday?.stringValue ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }

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

    private fun FirestoreDocument.toDomain(): FamilyMember? {
        val fields = this.fields ?: return null

        return FamilyMember(
            uid = this.name.substringAfterLast("/"),
            fullName = fields.fullName.stringValue,
            role = fields.role.stringValue,
            contactNumber = "",
            birthday = fields.birthday?.stringValue ?: "",
            birthplace = fields.birthplace?.stringValue ?: ""
        )
    }
}

@Serializable
private data class FirestoreResponse(
    val documents: List<FirestoreDocument>
)

@Serializable
private data class FirestoreDocument(
    val name: String,
    val fields: FirestoreUserFields? = null
)

@Serializable
private data class FirestoreUserFields(
    val familyId: FirestoreStringValue,
    val role: FirestoreStringValue,
    val fullName: FirestoreStringValue,
    val email: FirestoreStringValue? = null,
    val contactNumber: FirestoreStringValue? = null,
    val birthday: FirestoreStringValue? = null,
    val birthplace: FirestoreStringValue? = null,
    val isDeleted: FirestoreBooleanValue? = null
)

@Serializable
private data class FirestoreStringValue(val stringValue: String)

@Serializable
private data class FirestoreBooleanValue(val booleanValue: Boolean)

// new member
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

@Serializable
private data class FirestoreUserDocument(
    val fields: FirestoreUserFields
)
