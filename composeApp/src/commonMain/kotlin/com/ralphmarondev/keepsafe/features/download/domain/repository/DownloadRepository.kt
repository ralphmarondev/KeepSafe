package com.ralphmarondev.keepsafe.features.download.domain.repository

interface DownloadRepository {
    suspend fun syncAndSave()
}