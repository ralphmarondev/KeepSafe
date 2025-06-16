package com.ralphmarondev.keepsafe.family.data.repository

import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val getMembersApiService: GetMembersApiService
) : FamilyRepository {

    override suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember) {

    }

    override suspend fun getFamilyMembers(familyId: String): List<FamilyMember> {
        val result = getMembersApiService.getMembers(familyId)

        val memberList = result.map { doc ->
            val fields = doc.fields
            FamilyMember(
                uid = doc.name.split("/").last(),
                familyId = fields.familyId.stringValue,
                birthplace = fields.birthplace.stringValue,
                birthday = fields.birthday.stringValue,
                fullName = fields.fullName.stringValue,
                email = fields.fullName.stringValue,
                role = fields.role.stringValue,
                isDeleted = fields.isDeleted.booleanValue
            )
        }
        return memberList
    }

    override suspend fun getMemberDetails(uid: String): FamilyMember? {
        return null
    }

    override suspend fun updateFamilyMember(familyMember: FamilyMember) {

    }

    override suspend fun deleteFamilyMember(uid: String) {

    }
}