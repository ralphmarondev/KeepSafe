package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

@Serializable
data class MemberWithUid(
    val uid: String,
    val member: MemberList
)

class GetDetailsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getDetailsByUid(uid: String): MemberList? {
        return try {
            val response = httpClient.get("${Secrets.DATABASE_URL}/users/$uid")

            if (!response.status.isSuccess()) {
                println("Getting details failed: ${response.bodyAsText()}")
                return null
            }
            response.body()
        } catch (e: Exception) {
            println("Getting details error: ${e.message}")
            null
        }
    }

    suspend fun getDetailsByEmail(email: String): MemberWithUid? {
        return try {
            val response = httpClient.get("${Secrets.DATABASE_URL}/users")

            if (!response.status.isSuccess()) {
                println("Getting user list failed: ${response.bodyAsText()}")
                return null
            }
            val userList = response.body<FamilyMembersResponse>()
            val userDetails = userList.documents.firstOrNull {
                it.fields.email.stringValue == email
            } ?: return null

            val uid = userDetails.name.split("/").last()
            MemberWithUid(uid = uid, member = userDetails)
        } catch (e: Exception) {
            println("Getting details error: ${e.message}")
            null
        }
    }
}