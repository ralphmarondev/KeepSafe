package com.ralphmarondev.keepsafe.features.auth.data.repository

import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseAuthentication
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository

actual class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuthentication
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
            Result.Success(user)
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            Result.Error(message = e.message ?: "Login failed.")
        }
    }

    actual override suspend fun register(user: User): Result<User> {
        return try {
            val auth = firebaseAuth.register(
                email = user.email,
                password = user.password
            )
            val registeredUser = user.copy(
                uid = auth.localId,
                password = ""
            )
            println("User registered: $registeredUser")
            Result.Success(registeredUser)
        } catch (e: Exception) {
            println("Registration error: ${e.message}")
            Result.Error(message = e.message ?: "Registration failed.")
        }
    }

    actual override suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return false
    }
}