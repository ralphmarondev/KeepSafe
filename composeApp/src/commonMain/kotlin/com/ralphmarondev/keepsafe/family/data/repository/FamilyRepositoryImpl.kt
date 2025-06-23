package com.ralphmarondev.keepsafe.family.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterRequest
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.AddMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.DeleteMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.FieldBooleanValue
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.FieldStringValue
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.MemberFields
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.UpdateMemberApiService
import com.ralphmarondev.keepsafe.core.util.currentTimeMillis
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.first

class FamilyRepositoryImpl(
    private val getMembersApiService: GetMembersApiService,
    private val getDetailsApiService: GetDetailsApiService,
    private val deleteMemberApiService: DeleteMemberApiService,
    private val addMemberApiService: AddMemberApiService,
    private val updateMemberApiService: UpdateMemberApiService,
    private val preferences: AppPreferences,
    private val userDao: UserDao
) : FamilyRepository {

    override suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember) {
        val familyId = preferences.familyId().first()

        addMemberApiService.addMember(
            registerRequest = RegisterRequest(
                email = newFamilyMember.email ?: "",
                password = newFamilyMember.password ?: ""
            ),
            memberFields = MemberFields(
                birthplace = FieldStringValue(newFamilyMember.birthplace ?: ""),
                role = FieldStringValue(newFamilyMember.role ?: ""),
                familyId = FieldStringValue(familyId),
                birthday = FieldStringValue(newFamilyMember.birthday ?: ""),
                fullName = FieldStringValue(newFamilyMember.fullName ?: ""),
                email = FieldStringValue(newFamilyMember.email ?: ""),
                isDeleted = FieldBooleanValue(newFamilyMember.isDeleted == true),
            )
        )
    }

    override suspend fun getFamilyMembers(familyId: String): List<FamilyMember> {
        // if first_time_reading_family_members:
        //  - read from api
        //  - save to local database
        // else:
        //  - read from local database
        val isFirstTime = preferences.isFirstTimeReadingFamilyList().first() != false

        return if (isFirstTime) {
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
            val filteredMemberList = memberList.filter { member ->
                member.role != "Admin"
            }
            filteredMemberList.map { member ->
                userDao.upsert(
                    userEntity = UserEntity(
                        uid = member.uid ?: "",
                        familyId = member.familyId ?: "",
                        fullName = member.fullName ?: "",
                        email = member.email ?: "",
                        role = member.role ?: "",
                        relationship = "Relationship",
                        birthday = member.birthday ?: "",
                        birthplace = member.birthplace ?: "",
                        phoneNumber = "Phone number",
                        createDate = currentTimeMillis(),
                        isDeleted = member.isDeleted == true
                    )
                )
            }
            preferences.setFirstTimeReadingFamilyList(false)
            filteredMemberList
        } else {
            val familyMemberList = userDao.getAllUsers()
            val filteredMemberList = familyMemberList.filter { member ->
                member.role != "Admin"
            }
            filteredMemberList.map { member ->
                FamilyMember(
                    uid = member.uid,
                    familyId = member.familyId,
                    fullName = member.fullName,
                    email = member.email,
                    role = member.role,
                    birthday = member.birthday,
                    birthplace = member.birthplace,
                    isDeleted = member.isDeleted
                )
            }
        }
    }

    override suspend fun getMemberDetails(uid: String): FamilyMember? {
        val isFirstTime = preferences.isFirstTimeReadingFamilyList().first() != false

        if (isFirstTime) {
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
        } else {
            val result = userDao.getDetailByUId(uid)

            return FamilyMember(
                uid = result?.uid ?: "",
                familyId = result?.familyId ?: "",
                fullName = result?.fullName ?: "",
                email = result?.email ?: "",
                role = result?.role ?: "",
                birthday = result?.birthday ?: "",
                birthplace = result?.birthplace ?: "",
                isDeleted = result?.isDeleted == true
            )
        }
    }

    override suspend fun updateFamilyMember(familyMember: FamilyMember): Boolean {
        val result = updateMemberApiService.update(
            uid = familyMember.uid ?: "",
            memberFields = MemberFields(
                birthplace = FieldStringValue(familyMember.birthplace ?: ""),
                role = FieldStringValue(familyMember.role ?: ""),
                familyId = FieldStringValue(familyMember.familyId ?: ""),
                birthday = FieldStringValue(familyMember.birthday ?: ""),
                fullName = FieldStringValue(familyMember.fullName ?: ""),
                email = FieldStringValue(familyMember.email ?: ""),
                isDeleted = FieldBooleanValue(familyMember.isDeleted == true),
            )
        )
        return result
    }

    override suspend fun deleteFamilyMember(uid: String): Boolean {
        return deleteMemberApiService.delete(uid = uid)
    }
}