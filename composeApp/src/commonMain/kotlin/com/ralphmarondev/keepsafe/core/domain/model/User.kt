package com.ralphmarondev.keepsafe.core.domain.model

enum class Role {
    FAMILY_ADMIN,
    FAMILY_MEMBER
}

data class User(
    val id: Long = 0,
    val uid: String = "",
    val familyId: String = "",
    val familyName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "", // only used on registration
    val role: String = Role.FAMILY_MEMBER.name,
    val rank: Long = 0,

    // personal info
    val firstName: String = "",
    val middleName: String = "",
    val maidenName: String = "",
    val lastName: String = "",
    val nickname: String = "",
    val civilStatus: String = "",
    val religion: String = "",
    val gender: String = "",
    val birthday: String = "",
    val birthplace: String = "",
    val currentAddress: String = "",
    val permanentAddress: String = "",
    val phoneNumber: String = "",
    val photoUrl: String? = null,

    // health info
    val bloodType: String = "",
    val allergies: String = "",
    val medicalConditions: String = "",
    val emergencyContact: String = "",

    val createDate: Long = 0,
    val lastUpdateDate: Long = 0,
)