package com.ralphmarondev.keepsafe.family_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.AppPreferences
import com.ralphmarondev.keepsafe.family_list.data.network.FamilyListApiService
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FamilyListViewModel(
    private val familyListApiService: FamilyListApiService,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()


    init {
        viewModelScope.launch {
            val idToken = preferences.idToken().first()
            if (!idToken.isNullOrEmpty()) {
                val result = familyListApiService.getFamilyMembers(idToken)
                _familyMembers.value = result
            }
        }
    }
}