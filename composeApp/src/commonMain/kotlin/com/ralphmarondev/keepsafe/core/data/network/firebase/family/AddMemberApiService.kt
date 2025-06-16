package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterRequest
import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

@Serializable
data class CreateMemberRequest(
    val fields: MemberFields
)

class AddMemberApiService(
    private val httpClient: HttpClient,
    private val registerApiService: RegisterApiService
) {
    suspend fun addMember(
        registerRequest: RegisterRequest,
        memberFields: MemberFields
    ): Boolean {
        return try {
            val registerResponse = registerApiService.register(registerRequest)
            val uid = registerResponse?.localId ?: return false

            // 2025-06-16
            // NOTE: Yeah, we use PUT to create new user lol
            println("\n\nFirestore url: ${Secrets.userDetailsUrl}/$uid\n\n")
            val response = httpClient.post("${Secrets.userDetailsUrl}?documentId=$uid") {
                contentType(ContentType.Application.Json)
                setBody(CreateMemberRequest(fields = memberFields))
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            println("Add member failed: ${e.message}")
            false
        }
    }
}