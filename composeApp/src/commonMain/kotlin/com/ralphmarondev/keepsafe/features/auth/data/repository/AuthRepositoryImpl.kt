package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseAuth
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.core.mapper.toDomain
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseService: FirebaseService,
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : AuthRepository {
    override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val result = firebaseAuth.login(email = email, password = password)
            val firebaseUser = result.email

            if (firebaseUser.isNotEmpty()) {
                val userEntity = firebaseService.getDetailsByEmail(
                    email = email,
                    familyId = familyId
                )
                if (userEntity != null && userEntity.familyId == familyId) {
                    userDao.create(userEntity)
                    preferences.setFamilyId(familyId)
                    preferences.setCurrentUserEmail(email)
                    preferences.setCurrentUserRole(userEntity.role == Role.FAMILY_ADMIN)
                    preferences.setIsAuthenticated(true)

                    val user = userEntity.toDomain()
                    Result.Success(user)
                } else {
                    Result.Error("User details not found in cloud.")
                }
            } else {
                Result.Error("No user found.")
            }
        } catch (e: Exception) {
            Result.Error(message = e.message, throwable = e)
        }
    }

    override suspend fun register(user: User): Result<User> {
        return Result.Success(user)
    }

    override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return false
    }
}