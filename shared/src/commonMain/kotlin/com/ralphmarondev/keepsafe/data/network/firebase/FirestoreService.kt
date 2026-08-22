package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirestoreService(
    private val firestore: FirebaseFirestore
) {

    suspend fun createFamily(family: Family): Result<Family> {
        return try {
            val docRef = firestore.collection("families").document
            val newFamily = family.copy(
                uid = docRef.id,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            docRef.set(newFamily)
            Result.Success(newFamily)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to create family", throwable = e)
        }
    }

    suspend fun getFamilyById(familyUid: String): Result<Family> {
        return try {
            val document = firestore.collection("families")
                .document(familyUid)
                .get()

            if (document.exists) {
                val family: Family = document.data()
                Result.Success(family)
            } else {
                Result.Error("Family not found.")
            }
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to fetch family", throwable = e)
        }
    }
}