package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.common.currentTimeMillis
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class FirebaseService(
    private val client: HttpClient
) {
    @Suppress("UNCHECKED_CAST")
    private fun firestoreDocToUserEntity(doc: Map<String, Any?>): UserEntity {
        val fields = doc["fields"] as? Map<String, Any?> ?: emptyMap()
        fun stringVal(key: String) = (fields[key] as? Map<String, String>)?.get("stringValue") ?: ""
        fun longVal(key: String) = ((fields[key] as? Map<String, String>)
            ?.get("integerValue")?.toLongOrNull()) ?: 0

        fun boolVal(key: String) = ((fields[key] as? Map<String, String>)
            ?.get("booleanValue")?.toBoolean()) ?: false

        return UserEntity(
            uid = stringVal("uid"),
            familyId = stringVal("familyId"),
            username = stringVal("username"),
            email = stringVal("email"),
            role = stringVal("role"),
            rank = longVal("rank"),
            firstName = stringVal("firstName"),
            middleName = stringVal("middleName"),
            maidenName = stringVal("maidenName"),
            lastName = stringVal("lastName"),
            nickname = stringVal("nickname"),
            civilStatus = stringVal("civilStatus"),
            religion = stringVal("religion"),
            gender = stringVal("gender"),
            birthday = stringVal("birthday"),
            birthplace = stringVal("birthplace"),
            currentAddress = stringVal("currentAddress"),
            permanentAddress = stringVal("permanentAddress"),
            phoneNumber = stringVal("phoneNumber"),
            photoUrl = stringVal("photoUrl").ifEmpty { null },
            bloodType = stringVal("bloodType"),
            allergies = stringVal("allergies"),
            medicalConditions = stringVal("medicalConditions"),
            emergencyContact = stringVal("emergencyContact"),
            createDate = longVal("createdAt"),
            lastUpdateDate = longVal("lastUpdateDate"),
            isActive = boolVal("isActive")
        )
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getFamilyList(
        familyId: String,
        idToken: String
    ): List<UserEntity> {
        return try {
            val response: Map<String, Any?> = client.get(
                "${FirebaseConfig.FIRESTORE_BASE_URL}/families/$familyId/members"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $idToken")
            }.body()

            val documents = response["documents"] as? List<Map<String, Any?>> ?: emptyList()

            documents.mapNotNull { doc ->
                try {
                    firestoreDocToUserEntity(doc)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getDetailsByEmail(
        email: String,
        familyId: String,
        idToken: String
    ): UserEntity? {
        val query = mapOf(
            "structuredQuery" to mapOf(
                "from" to listOf(mapOf("collectionId" to "members")),
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
        val response: List<Map<String, Any?>> = client.post(
            "${FirebaseConfig.FIRESTORE_BASE_URL}/families/$familyId/members:runQuery"
        ) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $idToken")
            setBody(query)
        }.body()


        val doc = response.firstOrNull()?.get("document") as? Map<String, Any?> ?: return null
        return firestoreDocToUserEntity(doc)
    }

    suspend fun isFamilyIdTaken(familyId: String): Boolean {
        val response: Map<String, Any?> = client.get(
            "${FirebaseConfig.FIRESTORE_BASE_URL}/families/$familyId"
        ).body()
        return response.isNotEmpty()
    }

    suspend fun createFamily(
        familyId: String,
        familyName: String,
        createdBy: String,
        idToken: String
    ): Boolean {
        val payload = mapOf(
            "fields" to mapOf(
                "familyId" to mapOf("stringValue" to familyId),
                "familyName" to mapOf("stringValue" to familyName),
                "createdBy" to mapOf("stringValue" to createdBy),
                "createdAt" to mapOf(
                    "integerValue" to currentTimeMillis().toString()
                )
            )
        )

        client.patch("${FirebaseConfig.FIRESTORE_BASE_URL}/families/$familyId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $idToken")
            setBody(payload)
        }

        return true
    }

    suspend fun registerMemberToFamily(
        user: User,
        uid: String,
        idToken: String
    ): Boolean {
        return try {
            val payload = mapOf(
                "fields" to mapOf(
                    "uid" to mapOf("stringValue" to uid),
                    "familyId" to mapOf("stringValue" to user.familyId),
                    "username" to mapOf("stringValue" to user.username),
                    "email" to mapOf("stringValue" to user.email),
                    "role" to mapOf("stringValue" to user.role),
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
                    "photoUrl" to mapOf("stringValue" to (user.photoUrl ?: "")),
                    "bloodType" to mapOf("stringValue" to user.bloodType),
                    "allergies" to mapOf("stringValue" to user.allergies),
                    "medicalConditions" to mapOf("stringValue" to user.medicalConditions),
                    "emergencyContact" to mapOf("stringValue" to user.emergencyContact),
                    "createdAt" to mapOf("integerValue" to currentTimeMillis().toString()),
                    "lastUpdateDate" to mapOf("integerValue" to currentTimeMillis().toString()),
                    "isActive" to mapOf("booleanValue" to user.active.toString())
                )
            )

            client.patch("${FirebaseConfig.FIRESTORE_BASE_URL}/families/${user.familyId}/members/$uid") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $idToken")
                setBody(payload)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}