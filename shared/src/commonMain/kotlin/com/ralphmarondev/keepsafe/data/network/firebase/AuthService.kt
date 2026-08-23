package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class AuthService(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun register(account: Account): Result<Account> {
        return try {
            val formattedEmail = "${account.username}@keepsafe.com"

            val existingUserQuery = firestore.collection("users")
                .where { "username" equalTo account.username }
                .get()

            if (existingUserQuery.documents.isNotEmpty()) {
                return Result.Error(message = "Username '${account.username}' is already taken.")
            }

            val authResult = auth.createUserWithEmailAndPassword(formattedEmail, account.password)
            val uid = authResult.user?.uid
                ?: return Result.Error(message = "Failed to create user in Firebase Auth.")
            val createdAccount = account.copy(
                uid = uid,
                password = ""
            )
            Result.Success(createdAccount)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Registration failed", throwable = e)
        }
    }
}