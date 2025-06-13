package com.ralphmarondev.keepsafe.family_list.data.mapper

import com.ralphmarondev.keepsafe.family_list.data.model.FirestoreDocument
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember

fun FirestoreDocument.toDomain(): FamilyMember? {
    val f = fields
    return if (f.firstName != null && f.lastName != null && f.email != null) {
        val familyMember = FamilyMember(
            id = name,
            firstName = f.firstName.stringValue,
            middleName = f.middleName?.stringValue ?: "",
            lastName = f.lastName.stringValue,
            birthday = f.birthday?.stringValue ?: "",
            birthplace = f.birthplace?.stringValue ?: "",
            email = f.email.stringValue,
            phoneNumber = f.phoneNumber?.stringValue ?: "",
            role = f.role?.stringValue ?: "."
        )
        familyMember
    } else null
}
