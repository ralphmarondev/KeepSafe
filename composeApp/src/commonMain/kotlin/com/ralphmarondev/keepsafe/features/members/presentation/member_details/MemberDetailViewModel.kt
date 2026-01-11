package com.ralphmarondev.keepsafe.features.members.presentation.member_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberDetailViewModel(
    private val email: String,
    private val repository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemberDetailState())
    val state = _state.asStateFlow()

    init {
        loadMember()
    }

    fun onAction(action: MemberDetailAction) {
        when (action) {
            MemberDetailAction.NavigateBack -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            MemberDetailAction.ClearNavigation -> {
                _state.update {
                    it.copy(navigateBack = false)
                }
            }
        }
    }

    private fun loadMember() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val member = repository.getMemberByEmail(email = email) ?: return@launch
                _state.update {
                    it.copy(
                        member = member,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}