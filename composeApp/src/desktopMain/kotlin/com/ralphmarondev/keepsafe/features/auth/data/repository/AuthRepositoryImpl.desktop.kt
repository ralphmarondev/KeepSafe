package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseAuthentication
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

actual class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuthentication,
    private val firebaseService: FirebaseService,
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : AuthRepository {
    actual override suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        return try {
            val auth = firebaseAuth.login(email, password)

            val user = User(
                uid = auth.localId,
                email = auth.email,
                familyId = familyId
            )
            println("Login successful: $user")
            println("Checking if user exists on any family...")

            val userEntity = firebaseService.getUserByEmail(
                email = user.email,
                familyId = user.familyId,
                authToken = auth.idToken
            )
            println("User saved to firestore: $userEntity")

            Result.Success(user)
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            Result.Error(message = e.message ?: "Login failed.")
        }
    }

    actual override suspend fun register(user: User): Result<User> {
        return try {
            val firebaseUser = firebaseAuth.register(
                email = user.email,
                password = user.password
            )
            println("User registered: $firebaseUser")
            firebaseService.createFamily(
                familyId = user.familyId,
                familyName = user.familyName,
                createdBy = firebaseUser.localId,
                authToken = firebaseUser.idToken
            )
            firebaseService.registerUserToFamily(
                user = user,
                uid = firebaseUser.localId,
                authToken = firebaseUser.idToken,
            )
            Result.Success(user)
        } catch (e: Exception) {
            println("Registration error: ${e.message}")
            Result.Error(message = e.message ?: "Registration failed.")
        }
    }

    actual override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return false
    }
}