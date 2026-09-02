package com.ralphmarondev.keepsafe.data.network.firebase

import com.ralphmarondev.keepsafe.domain.enums.Role
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
    suspend fun login(
        username: String,
        password: String,
        familyCode: String
    ): Result<Member> {
        return try {
            val formattedEmail = "${username}@keepsafe.com"

            val authResult = auth.signInWithEmailAndPassword(
                email = formattedEmail,
                password = password
            )

            authResult.user?.uid
                ?: return Result.Error(
                    message = "Failed to authenticate user."
                )

            val accountReference = firestore
                .collection("accounts")
                .document(formattedEmail)

            if (!accountReference.get().exists) {
                return Result.Error(
                    message = "Account was not found."
                )
            }

            val memberReference = firestore
                .collection("families")
                .document(familyCode)
                .collection("members")
                .document(formattedEmail)

            val memberSnapshot = memberReference.get()

            if (!memberSnapshot.exists) {
                return Result.Error(
                    message = "Member account was not found in this family."
                )
            }

            val member = memberSnapshot.data<Member>()
            Result.Success(member)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                message = "Invalid credentials.",
                throwable = e
            )
        }
    }

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
            val firebaseUid = authResult.user?.uid
                ?: return Result.Error(
                    message = "Failed to create user in Firebase Auth."
                )

            val createdAccount = account.copy(
                uid = firebaseUid,
                password = "",
                role = Role.ADMIN
            )

            val createdMember = member.copy(
                uid = firebaseUid,
                familyCode = family.code
            )

            firestore.runTransaction {
                val userReference = firestore
                    .collection("accounts")
                    .document(formattedEmail)

                val familyReference = firestore
                    .collection("families")
                    .document(family.code)

                val memberReference = familyReference
                    .collection("members")
                    .document(formattedEmail)

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
            val formattedEmail = "${username}@keepsafe.com"

            val accountReference = firestore
                .collection("accounts")
                .document(formattedEmail)

            accountReference.get().exists
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

    suspend fun logout(): Result<Unit> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                message = e.message ?: "Logout failed.",
                throwable = e
            )
        }
    }
}