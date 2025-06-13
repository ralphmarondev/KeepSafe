package com.ralphmarondev.keepsafe.family_list.domain.model

data class FamilyMember(
    val id: String = "",
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val role: String,
    val birthday: String,
    val birthplace: String,
    val phoneNumber: String,
    val email: String
)
