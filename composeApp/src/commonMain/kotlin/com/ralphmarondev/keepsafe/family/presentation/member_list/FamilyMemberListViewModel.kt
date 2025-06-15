package com.ralphmarondev.keepsafe.family.presentation.member_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.family.data.network.FamilyApiService
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FamilyMemberListViewModel(
    private val familyApiService: FamilyApiService,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


    init {
        viewModelScope.launch {
            val idToken = preferences.idToken().first()
            val familyId = preferences.familyId().first() ?: "No familyId provided."
            if (!idToken.isNullOrEmpty()) {
                val result = familyApiService.getFamilyMembers(
                    idToken = idToken,
                    familyId = familyId
                )
                _familyMembers.value = result
            }
        }
    }

    fun setIsRefreshingValue(value: Boolean) {
        _isRefreshing.value = value
    }

    fun refresh(
        onDone: () -> Unit
    ) {
        if (_isRefreshing.value) {
            return
        }
        _isRefreshing.value = true
        viewModelScope.launch {
            val idToken = preferences.idToken().first()
            val familyId = preferences.familyId().first() ?: "No familyId provided."
            if (!idToken.isNullOrEmpty()) {
                val result = familyApiService.getFamilyMembers(
                    idToken = idToken,
                    familyId = familyId
                )
                _familyMembers.value = result
            }
            onDone()
            _isRefreshing.value = false
        }
    }
}