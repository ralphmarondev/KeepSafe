package com.ralphmarondev.keepsafe.core.data.local.database.mapper

import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        uid = uid,
        familyId = familyId,
        username = username,
        email = email,
        role = role,
        rank = rank,
        firstName = firstName,
        middleName = middleName,
        maidenName = maidenName,
        lastName = lastName,
        nickname = nickname,
        civilStatus = civilStatus,
        religion = religion,
        gender = gender,
        birthday = birthday,
        birthplace = birthplace,
        currentAddress = currentAddress,
        permanentAddress = permanentAddress,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl,
        bloodType = bloodType,
        allergies = allergies,
        medicalConditions = medicalConditions,
        emergencyContact = emergencyContact,
        active = isActive
    )
}


fun User.toEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        familyId = familyId,
        username = username,
        email = email,
        role = role,
        rank = rank,
        firstName = firstName,
        middleName = middleName,
        maidenName = maidenName,
        lastName = lastName,
        nickname = nickname,
        civilStatus = civilStatus,
        religion = religion,
        gender = gender,
        birthday = birthday,
        birthplace = birthplace,
        currentAddress = currentAddress,
        permanentAddress = permanentAddress,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl,
        bloodType = bloodType,
        allergies = allergies,
        medicalConditions = medicalConditions,
        emergencyContact = emergencyContact,
        isActive = active
    )
}