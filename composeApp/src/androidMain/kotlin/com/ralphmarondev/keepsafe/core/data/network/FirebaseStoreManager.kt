package com.ralphmarondev.keepsafe.core.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.User
import kotlinx.coroutines.tasks.await

class FirebaseStoreManager(
    private val store: FirebaseFirestore
) {
    suspend fun getDetailsByEmailAndFamilyId(
        email: String,
        familyId: String
    ): User? {
        val snapshot = store
            .collection("families")
            .document(familyId)
            .collection("members")
            .whereEqualTo("email", email)
            .limit(1)
            .get()
            .await()

        val doc = snapshot.documents.firstOrNull() ?: return null

        return try {
            User(
                uid = doc.id,
                familyId = doc.getString("familyId") ?: "",
                username = doc.getString("username") ?: "",
                email = doc.getString("email") ?: "",
                rank = doc.getLong("rank") ?: 0,
                role = doc.getString("role") ?: "",
                firstName = doc.getString("firstName") ?: "",
                middleName = doc.getString("middleName") ?: "",
                maidenName = doc.getString("maidenName") ?: "",
                lastName = doc.getString("lastName") ?: "",
                nickname = doc.getString("nickname") ?: "",
                civilStatus = doc.getString("civilStatus") ?: "",
                religion = doc.getString("religion") ?: "",
                gender = doc.getString("gender") ?: "",
                birthday = doc.getString("birthday") ?: "",
                birthplace = doc.getString("birthplace") ?: "",
                currentAddress = doc.getString("currentAddress") ?: "",
                permanentAddress = doc.getString("permanentAddress") ?: "",
                phoneNumber = doc.getString("phoneNumber") ?: "",
                photoUrl = doc.getString("photoUrl"),
                bloodType = doc.getString("bloodType") ?: "",
                allergies = doc.getString("allergies") ?: "",
                medicalConditions = doc.getString("medicalConditions") ?: "",
                emergencyContact = doc.getString("emergencyContact") ?: "",
                createDate = doc.getLong("createdAt") ?: 0
            )
        } catch (_: Exception) {
            null
        }
    }

    suspend fun registerToFamily(user: User): Result<User> {
        return try {
            createFamily(
                familyName = user.familyName,
                familyId = user.familyId,
                createdBy = user.uid
            )
            val newUser = mapOf(
                "uid" to user.uid,
                "familyId" to user.familyId,
                "username" to user.username,
                "email" to user.email,
                "rank" to user.rank,
                "role" to user.role,
                "firstName" to user.firstName,
                "middleName" to user.middleName,
                "maidenName" to user.maidenName,
                "lastName" to user.lastName,
                "nickname" to user.nickname,
                "civilStatus" to user.civilStatus,
                "religion" to user.religion,
                "gender" to user.gender,
                "birthday" to user.birthday,
                "birthplace" to user.birthplace,
                "currentAddress" to user.currentAddress,
                "permanentAddress" to user.permanentAddress,
                "phoneNumber" to user.phoneNumber,
                "photoUrl" to user.photoUrl,
                "bloodType" to user.bloodType,
                "allergies" to user.allergies,
                "medicalConditions" to user.medicalConditions,
                "emergencyContact" to user.emergencyContact,
                "createdAt" to System.currentTimeMillis()
            )
            store
                .collection("families")
                .document(user.familyId)
                .collection("members")
                .document(user.uid)
                .set(newUser)
                .await()

            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(message = "Failed to register user.", throwable = e)
        }
    }

    private suspend fun createFamily(
        familyId: String,
        familyName: String,
        createdBy: String
    ) {
        val ref = store
            .collection("families")
            .document(familyId)

        store.runTransaction { transaction ->
            val snapshot = transaction.get(ref)

            if (snapshot.exists()) {
                throw IllegalArgumentException("Family ID already exists")
            }

            val family = mapOf(
                "familyId" to familyId,
                "familyName" to familyName,
                "createdAt" to System.currentTimeMillis(),
                "createdBy" to createdBy
            )
            transaction.set(ref, family)
        }.await()
    }

    suspend fun isFamilyIdTaken(familyId: String): Boolean {
        val doc = store
            .collection("families")
            .document(familyId)
            .get()
            .await()

        return doc.exists()
    }
}