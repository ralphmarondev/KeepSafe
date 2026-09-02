package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.enums.Role
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class MemberService(
    private val auth: FirebaseAuth,
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

    suspend fun createMember(
        familyCode: String,
        account: Account,
        member: Member
    ): Result<Member> {
        return try {
            if (familyCode.isBlank()) {
                return Result.Error(
                    message = "Family code cannot be empty."
                )
            }

            if (account.username.isBlank()) {
                return Result.Error(
                    message = "Username cannot be empty."
                )
            }

            if (account.password.isBlank()) {
                return Result.Error(
                    message = "Password cannot be empty."
                )
            }

            val formattedEmail = "${account.username}@keepsafe.com"

            val accountReference = firestore
                .collection("accounts")
                .document(formattedEmail)

            val memberReference = membersCollection(familyCode)
                .document(formattedEmail)

            if (accountReference.get().exists) {
                return Result.Error(
                    message = "Username is already taken."
                )
            }

            if (memberReference.get().exists) {
                return Result.Error(
                    message = "A member with this username already exists in this family."
                )
            }

            val authResult = auth.createUserWithEmailAndPassword(
                email = formattedEmail,
                password = account.password
            )

            val firebaseUid = authResult.user?.uid
                ?: return Result.Error(
                    message = "Failed to create user in firebase auth."
                )

            val createdAccount = account.copy(
                uid = firebaseUid,
                password = "",
                role = Role.USER
            )

            val createdMember = member.copy(
                uid = firebaseUid,
                familyCode = familyCode
            )

            firestore.runTransaction {
                set(accountReference, createdAccount)
                set(memberReference, createdMember)
            }

            Result.Success(createdMember)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                message = "Failed to create member.",
                throwable = e
            )
        }
    }
}