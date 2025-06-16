package com.ralphmarondev.keepsafe.family.data.repository

import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterRequest
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.AddMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.DeleteMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.FieldBooleanValue
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.FieldStringValue
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.MemberFields
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val getMembersApiService: GetMembersApiService,
    private val getDetailsApiService: GetDetailsApiService,
    private val deleteMemberApiService: DeleteMemberApiService,
    private val addMemberApiService: AddMemberApiService
) : FamilyRepository {

    override suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember) {
        addMemberApiService.addMember(
            registerRequest = RegisterRequest(
                email = newFamilyMember.email ?: "",
                password = newFamilyMember.password ?: ""
            ),
            memberFields = MemberFields(
                birthplace = FieldStringValue(newFamilyMember.birthplace ?: ""),
                role = FieldStringValue(newFamilyMember.role ?: ""),
                familyId = FieldStringValue(newFamilyMember.familyId ?: ""),
                birthday = FieldStringValue(newFamilyMember.birthday ?: ""),
                fullName = FieldStringValue(newFamilyMember.fullName ?: ""),
                email = FieldStringValue(newFamilyMember.email ?: ""),
                isDeleted = FieldBooleanValue(newFamilyMember.isDeleted == true),
            )
        )
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
                email = fields.email.stringValue,
                role = fields.role.stringValue,
                isDeleted = fields.isDeleted.booleanValue
            )
        }
        return memberList
    }

    override suspend fun getMemberDetails(uid: String): FamilyMember? {
        val result = getDetailsApiService.getDetailsByUid(uid = uid)
        val fields = result?.fields
        return FamilyMember(
            uid = uid,
            familyId = fields?.familyId?.stringValue,
            birthplace = fields?.birthplace?.stringValue,
            birthday = fields?.birthday?.stringValue,
            fullName = fields?.fullName?.stringValue,
            email = fields?.email?.stringValue,
            role = fields?.role?.stringValue,
            isDeleted = fields?.isDeleted?.booleanValue
        )
    }

    override suspend fun updateFamilyMember(familyMember: FamilyMember) {

    }

    override suspend fun deleteFamilyMember(uid: String): Boolean {
        return deleteMemberApiService.delete(uid = uid)
    }
}