package com.ralphmarondev.keepsafe.family_list.data.model

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
    val birthday: FirestoreString?,
    val birthplace: FirestoreString?,
    val email: FirestoreString?,
    val first_name: FirestoreString?,
    val middle_name: FirestoreString?,
    val last_name: FirestoreString?,
    val phone_number: FirestoreString?
)

@Serializable
data class FirestoreString(val stringValue: String)
