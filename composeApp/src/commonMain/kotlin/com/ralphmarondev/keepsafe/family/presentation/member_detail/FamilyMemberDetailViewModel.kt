package com.ralphmarondev.keepsafe.family.presentation.member_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FamilyMemberDetailViewModel(
    private val memberId: String,
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

    private val _details = MutableStateFlow<FamilyMember?>(null)
    val detail = _details.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getDetailsUseCase(memberId)
            _details.value = result
        }
    }
}