package com.ralphmarondev.keepsafe.feature.account.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}