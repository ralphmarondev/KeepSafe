package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewMemberViewModel : ViewModel() {

    private val _state = MutableStateFlow(NewMemberState())
    val state = _state.asStateFlow()

    fun onAction(action: NewMemberAction) {

    }
}