package com.ralphmarondev.keepsafe.domain.model

import com.ralphmarondev.keepsafe.domain.enums.Role
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val uid: String = "",
    val username: String = "",
    val password: String = "",
    val role: Role = Role.USER
)