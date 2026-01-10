package com.ralphmarondev.keepsafe.core.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.keepsafe.core.data.local.database.entities.UserEntity
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import kotlinx.coroutines.tasks.await

class FirebaseService(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun getFamilyList(familyId: String): List<UserEntity> {
        val snapshot = fireStore
            .collection("families")
            .document(familyId)
            .collection("members")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            try {
                UserEntity(
                    uid = doc.id,
                    familyId = doc.getString("familyId") ?: "",
                    username = doc.getString("username") ?: "",
                    email = doc.getString("email") ?: "",
                    rank = doc.getLong("rank") ?: 0,
                    role = doc.getString("role") ?: Role.FAMILY_MEMBER.name,
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
                    createDate = doc.getLong("createdAt") ?: System.currentTimeMillis()
                )
            } catch (_: Exception) {
                null
            }
        }
    }

    suspend fun getUserByEmail(email: String, familyId: String): UserEntity? {
        val snapshot = fireStore
            .collection("families")
            .document(familyId)
            .collection("members")
            .whereEqualTo("email", email)
            .limit(1)
            .get()
            .await()

        val doc = snapshot.documents.firstOrNull() ?: return null

        return try {
            UserEntity(
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

    suspend fun getPhotoDownloadBytes(path: String): ByteArray {
        return storage.reference.child(path).getBytes(Long.MAX_VALUE).await()
    }

    suspend fun registerUserToFamily(
        user: User,
        uid: String,
    ): Result<User> {
        return try {
            var uploadedPhotoUrl: String? = null
            if (user.photoUri != null) {
                val storageRef = storage.reference
                    .child("families")
                    .child(user.familyId)
                    .child("members")
                    .child("$uid.jpg")

                storageRef.putFile(user.photoUri).await()
                uploadedPhotoUrl = storageRef.downloadUrl.await().toString()
            }

            val userData = mapOf(
                "uid" to uid,
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
                "photoUrl" to (uploadedPhotoUrl ?: user.photoUrl),
                "bloodType" to user.bloodType,
                "allergies" to user.allergies,
                "medicalConditions" to user.medicalConditions,
                "emergencyContact" to user.emergencyContact,
                "createdAt" to System.currentTimeMillis()
            )
            fireStore
                .collection("families")
                .document(user.familyId)
                .collection("members")
                .document(uid)
                .set(userData)
                .await()

            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(message = "Failed to register user ${user.email}", throwable = e)
        }
    }

    suspend fun isFamilyIdTaken(familyId: String): Boolean {
        val doc = fireStore
            .collection("families")
            .document(familyId)
            .get()
            .await()

        return doc.exists()
    }

    suspend fun createFamily(
        familyId: String,
        familyName: String,
        createdBy: String
    ) {
        val familyRef = fireStore
            .collection("families")
            .document(familyId)

        fireStore.runTransaction { transaction ->
            val snapshot = transaction.get(familyRef)

            if (snapshot.exists()) {
                throw IllegalStateException("Family ID already exists")
            }

            val familyData = mapOf(
                "familyId" to familyId,
                "familyName" to familyName,
                "createdAt" to System.currentTimeMillis(),
                "createdBy" to createdBy
            )
            transaction.set(familyRef, familyData)
        }.await()
    }
}