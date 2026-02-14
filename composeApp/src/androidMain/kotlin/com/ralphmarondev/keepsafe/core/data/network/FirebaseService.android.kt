package com.ralphmarondev.keepsafe.core.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User

actual class FirebaseService(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    actual suspend fun login(
        familyId: String,
        email: String,
        password: String
    ): Result<User> {
        return Result.Success(User())
    }

    actual suspend fun register(user: User): Result<User> {
        return Result.Success(User())
    }

    actual suspend fun isFamilyIdTaken(familyId: String): Boolean {
        TODO("Not yet implemented")
    }
}