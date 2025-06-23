package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

@Serializable
data class FamilyMembersResponse(
    val documents: List<MemberList>
)

@Serializable
data class MemberList(
    val name: String,
    val createTime: String,
    val updateTime: String,
    val fields: MemberFields
)

@Serializable
data class MemberFields(
    val birthplace: FieldStringValue,
    val role: FieldStringValue,
    val familyId: FieldStringValue,
    val birthday: FieldStringValue,
    val fullName: FieldStringValue,
    val email: FieldStringValue,
    val isDeleted: FieldBooleanValue
)

@Serializable
data class FieldStringValue(
    val stringValue: String
)

@Serializable
data class FieldBooleanValue(
    val booleanValue: Boolean
)

class GetMembersApiService(
    private val httpClient: HttpClient
) {
    suspend fun getMembers(familyId: String): List<MemberList> {
        return try {
            val response = httpClient.get("${Secrets.DATABASE_URL}/users")

            if (!response.status.isSuccess()) {
                println("Getting family members failed: ${response.bodyAsText()}")
                emptyList<FamilyMembersResponse>()
            }

            val membersList: FamilyMembersResponse = response.body()
            membersList.documents
                .filter { doc ->
                    val fields = doc.fields
                    fields.familyId.stringValue == familyId && !fields.isDeleted.booleanValue
                }
        } catch (e: Exception) {
            println("Getting family member error: ${e.message}")
            emptyList()
        }
    }
}