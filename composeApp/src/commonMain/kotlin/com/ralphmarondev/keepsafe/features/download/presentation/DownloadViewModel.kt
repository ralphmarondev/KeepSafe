package com.ralphmarondev.keepsafe.features.download.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DownloadViewModel(
    private val repository: DownloadRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DownloadState())
    val state = _state.asStateFlow()

    init {
        download()
    }

    fun onAction(action: DownloadAction) {
        when (action) {
            DownloadAction.Download -> {
                download()
            }

            DownloadAction.Retry -> {
                retry()
            }

            DownloadAction.SeeMyFamily -> {
                _state.update { it.copy(isSeeMyFamilyClick = true) }
            }
        }
    }

    private fun download() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        errorMessage = null, showErrorMessage = false,
                        isDownloading = true
                    )
                }
                repository.syncAndSave()
                _state.update {
                    it.copy(isDownloadCompleted = true)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed downloading family info. Error: ${e.message}",
                        showErrorMessage = true
                    )
                }
            } finally {
                _state.update {
                    it.copy(isDownloading = false)
                }
            }
        }
    }

    private fun retry() {

    }
}