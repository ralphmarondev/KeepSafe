package com.ralphmarondev.keepsafe.features.download.data.repository

import com.ralphmarondev.keepsafe.core.data.network.FirebaseService
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository

class DownloadRepositoryImpl(
    private val firebase: FirebaseService
) : DownloadRepository {
    override suspend fun syncAndSave() {
        firebase.syncAndSave()
    }
}