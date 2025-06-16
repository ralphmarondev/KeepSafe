package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMemberRequest(
    val fields: MemberFields
)

class UpdateMemberApiService(
    private val httpClient: HttpClient
) {
    suspend fun update(
        uid: String,
        memberFields: MemberFields
    ): Boolean {
        return try {
            val response = httpClient.patch("${Secrets.userDetailsUrl}/$uid") {
                contentType(ContentType.Application.Json)
                setBody(UpdateMemberRequest(fields = memberFields))
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            println("Update failed: ${e.message}")
            false
        }
    }
}