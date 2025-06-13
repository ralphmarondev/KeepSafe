package com.ralphmarondev.keepsafe.family_list.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirestoreResponse(
    val documents: List<FirestoreDocument> = emptyList()
)

@Serializable
data class FirestoreDocument(
    val name: String,
    val fields: FirestoreFields
)

@Serializable
data class FirestoreFields(
    @SerialName("birthday") val birthday: FirestoreString?,
    @SerialName("birthplace") val birthplace: FirestoreString?,
    @SerialName("email") val email: FirestoreString?,
    @SerialName("first_name") val firstName: FirestoreString?,
    @SerialName("middle_name") val middleName: FirestoreString?,
    @SerialName("last_name") val lastName: FirestoreString?,
    @SerialName("phone_number") val phoneNumber: FirestoreString?,
    @SerialName("role") val role: FirestoreString?
)

@Serializable
data class FirestoreString(val stringValue: String)
