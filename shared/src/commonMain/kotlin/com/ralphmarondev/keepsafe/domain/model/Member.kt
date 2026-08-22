package com.ralphmarondev.keepsafe.domain.model

data class Member(
    val uid: String = "",
    val familyCode: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val maidenName: String = ""
)
