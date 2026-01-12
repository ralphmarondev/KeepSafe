package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.File
import java.net.URLEncoder

class FirebaseService(
    private val client: HttpClient
) {
    private val projectId = System.getProperty("FIREBASE_PROJECT_ID")
        ?: error("MISSING_FIREBASE_PROJECT_ID")

    // ---------------------------
    // Firestore document helpers
    // ---------------------------
    private fun mapFirestoreDocumentToUser(doc: Map<String, Any?>): User {
        val fields = doc["fields"] as? Map<String, Map<String, Any?>> ?: emptyMap()
        return User(
            uid = fields["uid"]?.get("stringValue") as? String ?: "",
            familyId = fields["familyId"]?.get("stringValue") as? String ?: "",
            username = fields["username"]?.get("stringValue") as? String ?: "",
            email = fields["email"]?.get("stringValue") as? String ?: "",
            role = fields["role"]?.get("stringValue") as? String ?: Role.FAMILY_MEMBER.name,
            rank = (fields["rank"]?.get("integerValue") as? String)?.toLongOrNull() ?: 0,
            firstName = fields["firstName"]?.get("stringValue") as? String ?: "",
            middleName = fields["middleName"]?.get("stringValue") as? String ?: "",
            maidenName = fields["maidenName"]?.get("stringValue") as? String ?: "",
            lastName = fields["lastName"]?.get("stringValue") as? String ?: "",
            nickname = fields["nickname"]?.get("stringValue") as? String ?: "",
            civilStatus = fields["civilStatus"]?.get("stringValue") as? String ?: "",
            religion = fields["religion"]?.get("stringValue") as? String ?: "",
            gender = fields["gender"]?.get("stringValue") as? String ?: "",
            birthday = fields["birthday"]?.get("stringValue") as? String ?: "",
            birthplace = fields["birthplace"]?.get("stringValue") as? String ?: "",
            currentAddress = fields["currentAddress"]?.get("stringValue") as? String ?: "",
            permanentAddress = fields["permanentAddress"]?.get("stringValue") as? String ?: "",
            phoneNumber = fields["phoneNumber"]?.get("stringValue") as? String ?: "",
            photoUrl = fields["photoUrl"]?.get("stringValue") as? String,
            bloodType = fields["bloodType"]?.get("stringValue") as? String ?: "",
            allergies = fields["allergies"]?.get("stringValue") as? String ?: "",
            medicalConditions = fields["medicalConditions"]?.get("stringValue") as? String ?: "",
            emergencyContact = fields["emergencyContact"]?.get("stringValue") as? String ?: ""
        )
    }

    // ---------------------------
    // Get family members
    // ---------------------------
    suspend fun getFamilyList(
        familyId: String,
        authToken: String
    ): List<User> {
        val url =
            "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/families/$familyId/members"

        val response: Map<String, Any> = client.get(url) {
            header("Authorization", "Bearer $authToken")
        }.body()

        val documents = response["documents"] as? List<Map<String, Any>> ?: emptyList()
        return documents.map { mapFirestoreDocumentToUser(it) }
    }

    // ---------------------------
    // Get single user by email
    // ---------------------------
    suspend fun getUserByEmail(
        email: String,
        familyId: String,
        authToken: String
    ): User? {
        val encodedEmail = URLEncoder.encode(email, "UTF-8")
        val url =
            "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents:runQuery"

        val body = mapOf(
            "structuredQuery" to mapOf(
                "from" to listOf(
                    mapOf(
                        "collectionId" to "members",
                        "parent" to "projects/$projectId/databases/(default)/documents/families/$familyId"
                    )
                ),
                "where" to mapOf(
                    "fieldFilter" to mapOf(
                        "field" to mapOf("fieldPath" to "email"),
                        "op" to "EQUAL",
                        "value" to mapOf("stringValue" to email)
                    )
                ),
                "limit" to 1
            )
        )

        val response: List<Map<String, Any?>> = client.post(url) {
            header("Authorization", "Bearer $authToken")
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

        val docMap = response.firstOrNull()?.get("document") as? Map<String, Any?> ?: return null
        return mapFirestoreDocumentToUser(docMap)
    }

    // ---------------------------
    // Register user to family
    // ---------------------------
    suspend fun registerUserToFamily(
        user: User,
        uid: String,
        authToken: String
    ): Result<User> {
        return try {
            var uploadedPhotoUrl: String? = null
            user.photoUrl?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    uploadedPhotoUrl =
                        uploadPhoto(
                            path,
                            user.familyId,
                            uid,
                            authToken
                        )
                }
            }

            val docUrl =
                "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/families/${user.familyId}/members?documentId=$uid"

            val fields = mutableMapOf<String, Map<String, Any>>(
                "uid" to mapOf("stringValue" to uid),
                "familyId" to mapOf("stringValue" to user.familyId),
                "username" to mapOf("stringValue" to user.username),
                "email" to mapOf("stringValue" to user.email),
                "role" to mapOf("stringValue" to user.role),
                "photoUrl" to mapOf("stringValue" to (uploadedPhotoUrl ?: user.photoUrl ?: "")),
                "rank" to mapOf("integerValue" to user.rank.toString()),
                "firstName" to mapOf("stringValue" to user.firstName),
                "middleName" to mapOf("stringValue" to user.middleName),
                "maidenName" to mapOf("stringValue" to user.maidenName),
                "lastName" to mapOf("stringValue" to user.lastName),
                "nickname" to mapOf("stringValue" to user.nickname),
                "civilStatus" to mapOf("stringValue" to user.civilStatus),
                "religion" to mapOf("stringValue" to user.religion),
                "gender" to mapOf("stringValue" to user.gender),
                "birthday" to mapOf("stringValue" to user.birthday),
                "birthplace" to mapOf("stringValue" to user.birthplace),
                "currentAddress" to mapOf("stringValue" to user.currentAddress),
                "permanentAddress" to mapOf("stringValue" to user.permanentAddress),
                "phoneNumber" to mapOf("stringValue" to user.phoneNumber),
                "bloodType" to mapOf("stringValue" to user.bloodType),
                "allergies" to mapOf("stringValue" to user.allergies),
                "medicalConditions" to mapOf("stringValue" to user.medicalConditions),
                "emergencyContact" to mapOf("stringValue" to user.emergencyContact)
            )

            val body = mapOf("fields" to fields)

            client.post(docUrl) {
                header("Authorization", "Bearer $authToken")
                contentType(ContentType.Application.Json)
                setBody(body)
            }

            Result.Success(user.copy(photoUrl = uploadedPhotoUrl))
        } catch (e: Exception) {
            Result.Error("Failed to register user ${user.email}", e)
        }
    }

    // ---------------------------
    // Upload photo to storage
    // ---------------------------
    private suspend fun uploadPhoto(
        localPath: String,
        familyId: String,
        uid: String,
        authToken: String
    ): String {
        val file = File(localPath)
        if (!file.exists()) return localPath

        val storagePath = "families/$familyId/members/$uid.jpg"
        val url =
            "https://firebasestorage.googleapis.com/v0/b/$projectId.firebasestorage.app/o/${
                URLEncoder.encode(
                    storagePath,
                    "UTF-8"
                )
            }?uploadType=media"

        client.put(url) {
            setBody(file.readBytes())
            header("Authorization", "Bearer $authToken")
            header("Content-Type", "image/jpeg")
        }

        return "https://firebasestorage.googleapis.com/v0/b/$projectId.firebasestorage.app/o/${
            URLEncoder.encode(
                storagePath,
                "UTF-8"
            )
        }?alt=media"
    }

    // ---------------------------
    // Check if familyId exists
    // ---------------------------
    suspend fun isFamilyIdTaken(
        familyId: String,
        authToken: String
    ): Boolean {
        val url =
            "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/families/$familyId"

        return try {
            client.get(url) {
                header("Authorization", "Bearer $authToken")
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    // ---------------------------
    // Create family
    // ---------------------------
    suspend fun createFamily(
        familyId: String,
        familyName: String,
        createdBy: String,
        authToken: String
    ) {
        val docUrl =
            "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/families/$familyId"

        val body = mapOf(
            "fields" to mapOf(
                "familyId" to mapOf("stringValue" to familyId),
                "familyName" to mapOf("stringValue" to familyName),
                "createdAt" to mapOf("integerValue" to System.currentTimeMillis().toString()),
                "createdBy" to mapOf("stringValue" to createdBy)
            )
        )

        client.patch(docUrl) {
            header("Authorization", "Bearer $authToken")
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }
}
