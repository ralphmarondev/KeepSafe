package com.ralphmarondev.keepsafe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val uid: String = "",
    val familyCode: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    val imagePath: String? = null
)