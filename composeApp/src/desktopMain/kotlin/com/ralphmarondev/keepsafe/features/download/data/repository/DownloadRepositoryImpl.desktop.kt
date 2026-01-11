package com.ralphmarondev.keepsafe.features.download.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository

actual class DownloadRepositoryImpl :
    DownloadRepository {
    actual override suspend fun syncMembers(): List<User> {
        return emptyList()
    }
}