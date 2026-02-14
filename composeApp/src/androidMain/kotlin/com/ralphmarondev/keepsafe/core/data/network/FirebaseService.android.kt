package com.ralphmarondev.keepsafe.core.data.network

import android.util.Log
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

actual class FirebaseService(
    private val auth: FirebaseAuthManager,
    private val store: FirebaseStoreManager
) {
    actual suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        val firebaseUser = auth.login(email, password)
        return if (firebaseUser.user != null) {
            val user = store.getDetailsByEmailAndFamilyId(email, familyId)

            if (user != null && user.familyId == familyId) {
                Result.Success(user)
            } else {
                Result.Error(message = "User details not found in cloud.")
            }
        } else {
            Result.Error(message = "Invalid credentials.")
        }
    }

    actual suspend fun register(user: User): Result<User> {
        return try {
            val authResult = auth.register(user.email, user.password)
            val firebaseUser = authResult.user
                ?: return Result.Error(message = "Failed to create firebase account.")

            val newUser = user.copy(
                uid = firebaseUser.uid
            )

            store.registerToFamily(newUser)
            Result.Success(newUser)
        } catch (e: Exception) {
            Result.Error(message = "Error: ${e.message}")
        }
    }

    actual suspend fun isFamilyIdTaken(familyId: String): Boolean {
        return store.isFamilyIdTaken(familyId)
    }

    // TODO: IMPLEMENT THIS!
    actual suspend fun syncAndSave() {
        Log.d("Firebase", "Syncing...")
    }
}