package com.ralphmarondev.keepsafe.core.data.local.database.mapper

import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        uid = uid,
        familyId = familyId,
        familyName = familyName,
        username = username,
        email = email,
        password = password,
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
        createDate = createDate,
        lastUpdateDate = lastUpdateDate
    )
}


fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        uid = uid,
        familyId = familyId,
        familyName = familyName,
        username = username,
        email = email,
        password = password,
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
        createDate = createDate,
        lastUpdateDate = lastUpdateDate
    )
}