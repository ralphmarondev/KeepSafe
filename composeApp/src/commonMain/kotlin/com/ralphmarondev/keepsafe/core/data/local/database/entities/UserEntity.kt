package com.ralphmarondev.keepsafe.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ralphmarondev.keepsafe.core.util.currentTimeMillis

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val username: String,
    val password: String,
    val createDate: Long = currentTimeMillis(),
    val isDeleted: Boolean = false
)
