package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.util.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom

class DeleteMemberApiService(
    private val httpClient: HttpClient
) {
    suspend fun delete(uid: String): Boolean {
        return try {
            val response = httpClient.patch {
                url {
                    takeFrom(Secrets.userDetailsUrl)
                    appendPathSegments(uid)
                    parameters.append("updateMask.fieldPaths", "isDeleted")
                }
                setBody(
                    mapOf(
                        "fields" to mapOf(
                            "isDeleted" to mapOf("booleanValue" to true)
                        )
                    )
                )
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            println("Delete failed: ${e.message}")
            false
        }
    }
}