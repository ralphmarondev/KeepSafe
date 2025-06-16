package com.ralphmarondev.keepsafe.family.presentation.member_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FamilyMemberDetailViewModel(
    private val memberId: String,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _details = MutableStateFlow<FamilyMember?>(null)
    val detail = _details.asStateFlow()

    private val _role = MutableStateFlow("")
    val role = _role.asStateFlow()

    init {
        viewModelScope.launch {
            _role.value = preferences.role().first() ?: ""

            val result = getDetailsUseCase(memberId)
            _details.value = result
        }
    }
}