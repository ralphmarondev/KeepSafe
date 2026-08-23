package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore

class MemberService(
    private val firestore: FirebaseFirestore
) {
    suspend fun createMember(member: Member): Result<Member> {
        return try {
            val docRef = firestore.collection("members").document
            val newMember = member.copy(uid = docRef.id)
            docRef.set(newMember)
            Result.Success(newMember)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to create member.", throwable = e)
        }
    }

    suspend fun getMemberById(memberUid: String): Result<Member> {
        return try {
            if (memberUid.isBlank()) {
                return Result.Error(message = "Member UID cannot be empty.")
            }
            val document = firestore.collection("members")
                .document(memberUid)
                .get()
            if (document.exists) {
                val member: Member = document.data()
                Result.Success(member)
            } else {
                Result.Error("Member not found.")
            }
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to fetch member.", throwable = e)
        }
    }

    suspend fun getMembersByFamilyCode(familyCode: String): Result<List<Member>> {
        return try {
            if (familyCode.isBlank()) {
                return Result.Error("Family code cannot be empty.")
            }
            val querySnapshot = firestore.collection("members")
                .where { "familyCode" equalTo familyCode }
                .get()

            val members = querySnapshot.documents.map { doc ->
                doc.data<Member>()
            }
            Result.Success(members)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to fetch members.", throwable = e)
        }
    }
}