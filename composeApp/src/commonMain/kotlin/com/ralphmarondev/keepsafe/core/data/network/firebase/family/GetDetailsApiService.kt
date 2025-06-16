package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class GetDetailsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getDetails(uid: String): MemberList? {
        return try {
            val response = httpClient.get("${Secrets.userDetailsUrl}/$uid")

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
}