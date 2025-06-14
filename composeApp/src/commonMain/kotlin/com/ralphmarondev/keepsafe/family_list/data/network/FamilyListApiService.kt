package com.ralphmarondev.keepsafe.family_list.data.network

import com.ralphmarondev.keepsafe.core.util.Secrets
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class FamilyListApiService(
    private val client: HttpClient
) {
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
        val contactNumber: FirestoreStringValue? = null,
        val birthday: FirestoreStringValue? = null,
        val birthplace: FirestoreStringValue? = null,
        val isDeleted: FirestoreBooleanValue? = null
    )

    @Serializable
    private data class FirestoreStringValue(val stringValue: String)

    @Serializable
    private data class FirestoreBooleanValue(val booleanValue: Boolean)

    suspend fun getFamilyMembers(idToken: String, familyId: String): List<FamilyMember> {
        val url = Secrets.userDetailsUrl
        val response = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $idToken")
            }
        }

        if (!response.status.isSuccess()) {
            val erroBody = response.bodyAsText()
            throw Exception("Firestore access denied: $erroBody")
        }
        val body: FirestoreResponse = response.body()

        return body.documents
            .filter { doc ->
                doc.fields?.familyId?.stringValue == familyId &&
                        doc.fields.isDeleted?.booleanValue != true
            }
            .mapNotNull {
                it.toDomain()
            }
    }

    private fun FirestoreDocument.toDomain(): FamilyMember? {
        val fields = this.fields ?: return null

        return FamilyMember(
            uid = this.name.substringAfterLast("/"),
            fullName = fields.fullName.stringValue,
            role = fields.role.stringValue,
            contactNumber = fields.contactNumber?.stringValue ?: "",
            birthday = fields.birthday?.stringValue ?: "",
            birthplace = fields.birthplace?.stringValue ?: ""
        )
    }
}
