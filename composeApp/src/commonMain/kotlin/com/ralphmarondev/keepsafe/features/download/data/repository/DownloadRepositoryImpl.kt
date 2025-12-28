package com.ralphmarondev.keepsafe.features.download.data.repository

import com.ralphmarondev.keepsafe.core.data.local.database.dao.UserDao
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.core.mapper.toDomain
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DownloadRepositoryImpl(
    private val firebaseService: FirebaseService,
    private val userDao: UserDao,
    private val preferences: AppPreferences
) : DownloadRepository {
    override suspend fun syncMembers(): List<User> {
        val familyId = preferences.familyId.first()

        return withContext(Dispatchers.IO) {
            val firebaseMembers = firebaseService.getFamilyList(
                familyId = familyId,
                idToken = preferences.idToken.first()
            )
            userDao.clearUsersTable()

            firebaseMembers.map { member ->
                userDao.create(member)
                member.toDomain()
            }
        }
    }
}