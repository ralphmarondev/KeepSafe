package com.ralphmarondev.keepsafe.features.members.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberListViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemberListState())
    val state: StateFlow<MemberListState> = _state.asStateFlow()

    init {
        setCurrentUserRole()
        loadMembers()
    }

    fun onAction(action: MemberListAction) {
        when (action) {
            is MemberListAction.NavigateToDetails -> {
                _state.update {
                    it.copy(
                        navigateToDetails = true,
                        selectedMemberEmail = action.email
                    )
                }
            }

            MemberListAction.NavigateToAccount -> {
                _state.update {
                    it.copy(navigateToAccount = true)
                }
            }

            MemberListAction.NavigateToNotification -> {
                _state.update {
                    it.copy(navigateToNotification = true)
                }
            }

            MemberListAction.ClearNavigation -> {
                _state.update {
                    it.copy(
                        navigateToNewFamilyMember = false,
                        navigateToDetails = false,
                        navigateToAccount = false,
                        navigateToNotification = false
                    )
                }
            }

            MemberListAction.Refresh -> {
                refresh()
            }

            MemberListAction.NavigateToNewFamilyMember -> {
                _state.update {
                    it.copy(navigateToNewFamilyMember = true)
                }
            }

            is MemberListAction.Search -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query
                    )
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            delay(800)
            loadMembers()
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private fun loadMembers() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val members = repository.getAllMembers()
                _state.update {
                    it.copy(
                        members = members,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        members = emptyList(),
                        errorMessage = e.localizedMessage,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun setCurrentUserRole() {
        viewModelScope.launch {
            _state.update {
                it.copy(isCurrentUserAdmin = repository.isCurrentUserAdmin())
            }
        }
    }
}
