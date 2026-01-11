package com.ralphmarondev.keepsafe.features.download.presentation

sealed interface DownloadAction {
    data object Download : DownloadAction
    data object SeeMyFamily : DownloadAction
}