package com.ralphmarondev.keepsafe.family.domain.model

data class NewFamilyMember(
    val uid: String? = null,
    val birthplace: String? = null,
    val role: String? = null,
    val familyId: String? = null,
    val birthday: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val isDeleted: Boolean? = false,
    val password: String? = null
)
