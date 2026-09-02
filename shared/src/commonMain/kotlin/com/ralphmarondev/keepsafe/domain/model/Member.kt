package com.ralphmarondev.keepsafe.domain.model

import com.ralphmarondev.keepsafe.domain.enums.RelationshipToHead
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val uid: String = "",
    val familyCode: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    val birthday: String = "",
    val contactNumber: String = "",
    val imagePath: String = "",
    val relationToHead: RelationshipToHead = RelationshipToHead.SELF
)