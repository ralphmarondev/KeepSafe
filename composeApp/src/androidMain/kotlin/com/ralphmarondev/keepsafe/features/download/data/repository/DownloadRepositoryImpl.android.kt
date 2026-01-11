package com.ralphmarondev.keepsafe.features.download.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import com.ralphmarondev.keepsafe.core.common.PhotoStorage
import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.database.mapper.toDomain
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

actual class DownloadRepositoryImpl(
    private val firebaseService: FirebaseService,
    private val userDao: UserDao,
    private val context: Context,
    private val preferences: AppPreferences
) : DownloadRepository {
    actual override suspend fun syncMembers(): List<User> {
        val familyId = preferences.familyId.first()

        return withContext(Dispatchers.IO) {
            val firebaseMembers = firebaseService.getFamilyList(familyId = familyId)
            userDao.clearUsersTable()

            firebaseMembers.map { member ->
                val localPhotoPath = member.photoUrl?.let {
                    downloadAndSavePhoto(memberId = member.uid)
                }
                val updatedMember = member.copy(photoUrl = localPhotoPath)
                userDao.create(updatedMember)
                updatedMember.toDomain()
            }
        }
    }

    private suspend fun downloadAndSavePhoto(memberId: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val familyId = preferences.familyId.first()
                val storagePath = "families/$familyId/members/$memberId.jpg"

                val bytes = firebaseService.getPhotoDownloadBytes(storagePath)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                PhotoStorage.saveBitmap(
                    context = context,
                    bitmap = bitmap,
                    fileName = "$familyId/$memberId.jpg"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}