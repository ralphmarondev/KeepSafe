package com.ralphmarondev.keepsafe.family.domain.model

data class FamilyMember(
    val uid: String? = null,
    val fullName: String? = null,
    val birthday: String? = null,
    val birthplace: String? = null,
    val email: String? = null,
    val password: String? = null,
    val contactNumber: String? = null,
    val role: String? = null,
    val familyId: String? = null,
    val isDeleted: Boolean? = null
)