package com.ralphmarondev.keepsafe.family.presentation.member_detail

import androidx.lifecycle.ViewModel
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FamilyMemberDetailViewModel(
    private val memberId: String
) : ViewModel() {

    private val _details = MutableStateFlow<FamilyMember?>(null)
    val detail = _details.asStateFlow()

}