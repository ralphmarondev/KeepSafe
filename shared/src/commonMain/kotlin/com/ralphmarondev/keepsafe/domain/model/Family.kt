package com.ralphmarondev.keepsafe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Family(
    val uid: String = "",
    val code: String = "",
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false
)