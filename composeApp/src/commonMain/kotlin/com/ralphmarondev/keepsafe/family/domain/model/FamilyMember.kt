package com.ralphmarondev.keepsafe.family.domain.model

data class FamilyMember(
    val uid: String,
    val fullName: String,
    val role: String,
    val contactNumber: String,
    val birthday: String,
    val birthplace: String
)