package com.ralphmarondev.keepsafe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val uid: String = "",
    val username: String = "",
    val password: String = "",
    val memberUid: String? = null
)