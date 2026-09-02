package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore

class MemberService(
    private val firestore: FirebaseFirestore
) {
    private fun membersCollection(familyCode: String) = firestore
        .collection("families")
        .document(familyCode)
        .collection("members")

    suspend fun getMembersByFamilyCode(familyCode: String): Result<List<Member>> {
        return try {
            if (familyCode.isBlank()) {
                return Result.Error(
                    message = "Family code cannot be empty."
                )
            }

            val querySnapshot = membersCollection(familyCode).get()

            val members = querySnapshot.documents.map { doc ->
                doc.data<Member>()
            }
            Result.Success(members)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to fetch members.", throwable = e)
        }
    }
}