package com.ralphmarondev.keepsafe.family_list.data.mapper

import com.ralphmarondev.keepsafe.family_list.data.model.FirestoreDocument
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember

fun FirestoreDocument.toDomain(): FamilyMember? {
    val f = fields
    return if (f.first_name != null && f.last_name != null && f.email != null) {
        FamilyMember(
            firstName = f.first_name.stringValue,
            middleName = f.middle_name?.stringValue ?: "",
            lastName = f.last_name.stringValue,
            birthDay = f.birthday?.stringValue ?: "",
            birthPlace = f.birthplace?.stringValue ?: "",
            email = f.email.stringValue,
            contactNumber = f.phone_number?.stringValue ?: "",
            role = ""
        )
    } else null
}
