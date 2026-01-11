package com.ralphmarondev.keepsafe.features.members.data.repository

import android.content.Context
import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toDomain
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseAuthentication
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository
import kotlinx.coroutines.flow.first
import java.io.File

actual class MemberRepositoryImpl(
    private val userDao: UserDao,
    private val preferences: AppPreferences,
    private val firebaseAuth: FirebaseAuthentication,
    private val firebaseService: FirebaseService,
    private val context: Context
) : MemberRepository {
    actual override suspend fun getMemberByEmail(email: String): User? {
        val userEntity = userDao.getDetailsByEmail(email = email)
        return userEntity?.toDomain()
    }

    actual override suspend fun getAllMembers(): List<User> {
        val familyId = preferences.familyId.first()
        val userEntities = userDao.getUsersByFamilyId(familyId = familyId).first()
        val users = userEntities.map { it.toDomain() }
        return users.filter { it.role == Role.FAMILY_MEMBER.name }
    }

    actual override suspend fun saveNewMember(user: User): Result<User> {
        return try {
            val newMember = user.copy(
                familyId = preferences.familyId.first()
            )
            val authResult = firebaseAuth.register(
                email = newMember.email,
                password = newMember.password
            )
            val firebaseUser = authResult.user
                ?: return Result.Error("Failed to create firebase account.")

            firebaseService.registerUserToFamily(
                user = newMember,
                uid = firebaseUser.uid
            )

            val userEntity = newMember.copy(
                uid = firebaseUser.uid,
                photoUrl = newMember.photoUrl // localPhotoPath ?: newMember.photoUrl
            ).toEntity()
            userDao.create(userEntity)

            Result.Success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Registering new member failed.", throwable = e)
        }
    }

    actual override suspend fun isCurrentUserAdmin(): Boolean {
        return preferences.isCurrentUserFamilyAdmin.first()
    }
}