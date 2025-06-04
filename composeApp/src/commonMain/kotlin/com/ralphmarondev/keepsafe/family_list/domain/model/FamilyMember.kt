package com.ralphmarondev.keepsafe.family_list.domain.model

data class FamilyMember(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val role: String,
    val birthDay: String,
    val birthPlace: String,
    val contactNumber: String,
    val email: String
)
