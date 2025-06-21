package com.ralphmarondev.keepsafe.settings.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.core.util.currentTimeMillis
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.settings.domain.repository.SettingRepository
import kotlinx.coroutines.flow.first

class SettingRepositoryImpl(
    private val preferences: AppPreferences,
    private val userDao: UserDao,
    private val getMembersApiService: GetMembersApiService
) : SettingRepository {

    override suspend fun syncWithFirebase() {
        val familyId = preferences.familyId().first() ?: ""
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

        userDao.deleteAllUsers()

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
    }

    override suspend fun logout() {
        preferences.clearAccountInfo()
        preferences.setFirstTimeReadingFamilyList(true)
        userDao.deleteAllUsers()
    }
}