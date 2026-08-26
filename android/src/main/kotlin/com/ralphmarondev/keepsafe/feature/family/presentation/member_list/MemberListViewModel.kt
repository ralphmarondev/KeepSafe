package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
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

    }

    private fun loadMembers(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            val result = memberRepository.getFamilyInformation()
            if (result.isSuccess) {
                val family = (result as Result.Success).data
                _state.update { it.copy(family = family) }
            }
            val members = memberRepository.getMembers()
            _state.update { it.copy(members = members) }
        }
    }
}