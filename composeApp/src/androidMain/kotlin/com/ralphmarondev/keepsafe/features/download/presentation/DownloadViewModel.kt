package com.ralphmarondev.keepsafe.features.download.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DownloadViewModel(
    application: Application,
    private val repository: DownloadRepository
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(DownloadState())
    val state = _state.asStateFlow()

    init {
        onAction(DownloadAction.Download)
    }

    fun onAction(action: DownloadAction) {
        when (action) {
            DownloadAction.Download -> {
                download()
            }

            DownloadAction.SeeMyFamily -> {
                _state.update {
                    it.copy(isSeeMyFamilyClick = true)
                }
            }
        }
    }

    private fun download() {
        viewModelScope.launch {
            _state.update { it.copy(isDownloading = true) }
            try {
                repository.syncMembers()
                delay(3000)
                _state.update { it.copy(isDownloading = false, isDownloadCompleted = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDownloading = false,
                        isError = true,
                        errorMessage = e.localizedMessage
                    )
                }
            }
        }
    }
}