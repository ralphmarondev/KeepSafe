package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore

class MemberService(
    private val firestore: FirebaseFirestore
) {
    private val membersCollection = firestore
        .collection("members")

    suspend fun createMember(member: Member): Result<Member> {
        return try {
            if (member.uid.isBlank()) {
                return Result.Error(message = "Member UID cannot be empty.")
            }

            val memberReference = membersCollection.document(member.uid)
            memberReference.set(member)
            Result.Success(member)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to create member.", throwable = e)
        }
    }

    suspend fun getMemberById(memberUid: String): Result<Member> {
        return try {
            if (memberUid.isBlank()) {
                return Result.Error(message = "Member UID cannot be empty.")
            }
            val document = membersCollection
                .document(memberUid)
                .get()

            if (!document.exists) {
                return Result.Error(message = "Member not found.")
            }
            val member = document.data<Member>()
            Result.Success(member)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Failed to fetch member.", throwable = e)
        }
    }

    suspend fun getMembersByFamilyCode(familyCode: String): Result<List<Member>> {
        return try {
            if (familyCode.isBlank()) {
                return Result.Error("Family code cannot be empty.")
            }
            val querySnapshot = membersCollection
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