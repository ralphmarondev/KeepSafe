package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FamilyService(
    private val firestore: FirebaseFirestore
) {
    suspend fun createFamily(family: Family): Result<Family> {
        return try {
            if (family.code.isBlank()) {
                return Result.Error(message = "Family code cannot be empty.")
            }

            val docRef = firestore.collection("families").document(family.code)
            val existingDoc = docRef.get()
            if (existingDoc.exists) {
                return Result.Error(message = "Family code '${family.code}' is already taken.")
            }

            val newFamily = family.copy(
                uid = family.uid.ifBlank { docRef.id },
                code = family.code,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            docRef.set(newFamily)
            Result.Success(newFamily)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to create family", throwable = e)
        }
    }

    suspend fun getFamilyByCode(familyCode: String): Result<Family> {
        return try {
            if (familyCode.isBlank()) {
                return Result.Error(message = "Family code cannot be empty.")
            }

            val document = firestore.collection("families")
                .document(familyCode)
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