package com.ralphmarondev.keepsafe.family.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetFamilyMembersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FamilyMemberListViewModel(
    private val preferences: AppPreferences,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
) : ViewModel() {

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    init {
        refresh()
    }

    fun refresh() {
        if (_isRefreshing.value) {
            return
        }
        _isRefreshing.value = true
        viewModelScope.launch {
            val idToken = preferences.idToken().first() ?: ""
            val familyId = preferences.familyId().first() ?: ""

            val response = getFamilyMembersUseCase(
                idToken = idToken,
                familyId = familyId,
            )

            if (response.success) {
                _familyMembers.value = response.data ?: emptyList()
            }
            _response.value = Result(
                success = response.success,
                message = response.message
            )
            _isRefreshing.value = false
        }
    }
}