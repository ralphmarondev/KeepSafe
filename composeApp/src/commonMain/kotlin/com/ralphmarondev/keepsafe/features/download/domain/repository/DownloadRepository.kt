package com.ralphmarondev.keepsafe.features.download.domain.repository

import com.ralphmarondev.keepsafe.core.domain.model.User

interface DownloadRepository {
    suspend fun syncMembers(): List<User>
}