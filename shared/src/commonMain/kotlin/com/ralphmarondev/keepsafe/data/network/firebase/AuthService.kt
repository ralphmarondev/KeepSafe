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

            Log.d("AuthService", "Auth user created: $uid")
            Log.d("AuthService", "Starting Firestore transaction")
            firestore.runTransaction {
                Log.d("AuthService", "Inside Firestore transaction")
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

                Log.d("AuthService", "Writing user")
                set(userReference, createdAccount)

                Log.d("AuthService", "Writing family")
                set(familyReference, family)

                Log.d("AuthService", "Writing member")
                set(memberReference, createdMember)

                set(
                    counterReference,
                    mapOf(
                        "lastCode" to family.code
                            .removePrefix("FAM-")
                            .toLong()
                    )
                )

                Log.d("AuthService", "Transaction writes prepared.")
            }
            Log.d("AuthService", "Firestore transaction completed.")
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