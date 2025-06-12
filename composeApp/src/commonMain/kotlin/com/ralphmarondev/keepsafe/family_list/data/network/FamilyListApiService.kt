package com.ralphmarondev.keepsafe.family_list.data.network

import com.ralphmarondev.keepsafe.COLLECTION_PATH
import com.ralphmarondev.keepsafe.PROJECT_ID
import com.ralphmarondev.keepsafe.family_list.data.mapper.toDomain
import com.ralphmarondev.keepsafe.family_list.data.model.FirestoreResponse
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.isSuccess

class FamilyListApiService(
    private val client: HttpClient
) {
    suspend fun getFamilyMembers(idToken: String): List<FamilyMember> {
        val url =
            "https://firestore.googleapis.com/v1/projects/$PROJECT_ID/databases/(default)/documents/$COLLECTION_PATH"

        val response = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $idToken")
            }
        }

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            println("Firestore error: $errorBody")
            throw Exception("Firestore access denied: $errorBody")
        }

        val body: FirestoreResponse = response.body()
        return body.documents.mapNotNull { it.toDomain() }
    }
}