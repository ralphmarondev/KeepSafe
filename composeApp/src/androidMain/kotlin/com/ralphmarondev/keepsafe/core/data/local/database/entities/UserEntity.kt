package com.ralphmarondev.keepsafe.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uid: String = "",
    val familyId: String = "",
    val username: String = "",
    val email: String = "",
    val role: String = "",
    val rank: Long = 0, // order in showing to ui

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

    val createDate: Long = System.currentTimeMillis(),
    val lastUpdateDate: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)