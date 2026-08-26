package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberListViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemberListState())
    val state = _state.asStateFlow()

    init {
        loadMembers()
    }

    fun onAction(action: MemberListAction) {
        when (action) {
            MemberListAction.LoadMembers -> loadMembers()
            MemberListAction.Refresh -> loadMembers(isRefreshing = true)
            MemberListAction.NewMember -> newMember()
            MemberListAction.NavigateToProfile -> navigateToProfile()
            MemberListAction.ClearNavigation -> clearNavigation()
            is MemberListAction.MemberSelected -> memberSelected(action.member)
        }
    }

    private fun loadMembers(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            val result = memberRepository.getFamilyInformation()
            if (result.isSuccess) {
                val family = (result as Result.Success).data
                _state.update { it.copy(family = family) }
            }
            if (isRefreshing) {
                delay(500)
            }
            val members = memberRepository.getMembers()
            _state.update { it.copy(members = members) }
        }
    }

    private fun newMember() {
        _state.update { it.copy(navigateToNewMember = true) }
    }

    private fun navigateToProfile() {
        _state.update { it.copy(navigateToProfile = true) }
    }

    private fun clearNavigation() {
        _state.update {
            it.copy(
                navigateToNewMember = false,
                navigateToProfile = false,
                navigateToMemberDetail = false
            )
        }
    }

    private fun memberSelected(member: Member) {
        _state.update {
            it.copy(
                selectedMember = member,
                navigateToMemberDetail = true
            )
        }
    }
}