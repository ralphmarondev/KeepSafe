package com.ralphmarondev.keepsafe.features.download.presentation

data class DownloadState(
    val isDownloading: Boolean = false,
    val isDownloadCompleted: Boolean = false,
    val isSeeMyFamilyClick: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)