package com.ralphmarondev.keepsafe.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ralphmarondev.keepsafe.core.util.currentTimeMillis

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val uid: String,
    val familyId: String,
    val fullName: String,
    val email: String,
    val role: String,
    val relationship: String,
    val birthday: String,
    val birthplace: String,
    val phoneNumber: String,
    val createDate: Long = currentTimeMillis(),
    val isDeleted: Boolean = false
)
