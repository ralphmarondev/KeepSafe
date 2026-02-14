package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toEntity
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebase: FirebaseService,
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : AuthRepository {
    override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        val result = firebase.login(familyId, email, password)

        when (result) {
            is Result.Error -> {}
            Result.Loading -> {}
            is Result.Success<User> -> {
                val user = result.data
                userDao.create(user.toEntity())
                preferences.setFamilyId(user.familyId)
                preferences.setCurrentUserEmail(user.email)
                preferences.setCurrentUserRole(user.role == Role.FAMILY_ADMIN.name)
                preferences.setIsAuthenticated(true)
            }
        }
        return result
    }

    override suspend fun register(user: User): Result<User> {
        val result = firebase.register(user)

        when (result) {
            is Result.Error -> Unit
            Result.Loading -> Unit
            is Result.Success<User> -> {
                val user = result.data
                userDao.create(user.toEntity())
                preferences.setFamilyId(user.familyId)
                preferences.setCurrentUserEmail(user.email)
                preferences.setCurrentUserRole(user.role == Role.FAMILY_ADMIN.name)
            }
        }
        return result
    }

    override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return firebase.isFamilyIdTaken(familyId)
    }
}