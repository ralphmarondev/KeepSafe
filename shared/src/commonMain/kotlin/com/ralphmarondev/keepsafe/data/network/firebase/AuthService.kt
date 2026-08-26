package com.ralphmarondev.keepsafe.data.network.firebase

import android.util.Log
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class AuthService(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun register(
        account: Account,
        family: Family,
        member: Member
    ): Result<Family> {
        return try {
            val formattedEmail = "${account.username}@keepsafe.com"
            val authResult = auth.createUserWithEmailAndPassword(
                formattedEmail,
                account.password
            )
            val uid = authResult.user?.uid
                ?: return Result.Error(message = "Failed to create user in Firebase Auth.")

            val createdAccount = account.copy(
                uid = uid,
                password = "",
                memberUid = uid
            )

            val createdMember = member.copy(
                uid = uid,
                familyCode = family.code
            )

            firestore.runTransaction {
                val userReference = firestore
                    .collection("users")
                    .document(uid)

                val familyReference = firestore
                    .collection("families")
                    .document(family.code)

                val memberReference = firestore
                    .collection("members")
                    .document(uid)

                val counterReference = firestore
                    .collection("familyCodes")
                    .document("counter")

                set(userReference, createdAccount)
                set(familyReference, family)
                set(memberReference, createdMember)

                set(
                    counterReference,
                    mapOf(
                        "lastCode" to family.code
                            .removePrefix("FAM-")
                            .toLong()
                    )
                )
            }
            Result.Success(family)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                message = if (e.message?.contains(
                        "email-already-in-use",
                        ignoreCase = true
                    ) == true
                ) {
                    "Username is already taken."
                } else {
                    e.message ?: "Registration failed."
                }, throwable = e
            )
        }
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return try {
            val result = firestore
                .collection("users")
                .where {
                    "username" equalTo username
                }.get()

            result.documents.isNotEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun generateFamilyCode(): String {
        return try {
            val counterReference = firestore
                .collection("familyCodes")
                .document("counter")

            val snapshot = counterReference.get()
            val lastCode = if (snapshot.exists) {
                snapshot.get<Long>("lastCode").toInt()
            } else {
                0
            }
            (lastCode + 1)
                .toString()
                .padStart(4, '0')
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}