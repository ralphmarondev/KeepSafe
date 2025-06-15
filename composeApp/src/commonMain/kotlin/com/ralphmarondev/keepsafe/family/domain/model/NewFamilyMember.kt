package com.ralphmarondev.keepsafe.family.domain.model

data class NewFamilyMember(
    val fullName: String,
    val role: String,
    val birthday: String,
    val birthplace: String,
    val email: String,
    val password: String,
    val familyId: String
)
