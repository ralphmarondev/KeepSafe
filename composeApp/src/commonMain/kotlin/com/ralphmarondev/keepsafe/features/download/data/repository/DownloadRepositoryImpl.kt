package com.ralphmarondev.keepsafe.features.download.data.repository

import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository

expect class DownloadRepositoryImpl : DownloadRepository {
    override suspend fun syncMembers(): List<User>
}